package com.mrxu.server.processor.callback;

import com.alibaba.fastjson.JSON;
import com.mrxu.common.ImCommandCode;
import com.mrxu.common.ResponseStatus;
import com.mrxu.common.domain.biz.Command;
import com.mrxu.common.exception.ConnectionInvalidException;
import com.mrxu.common.exception.ConnectionNotWritableException;
import com.mrxu.common.utils.RemotingUtil;
import com.mrxu.remote.domain.RemotingCommand;
import com.mrxu.remote.domain.request.ImRequestCommand;
import com.mrxu.remote.domain.response.ImResponseCommand;
import com.mrxu.remote.service.MessageSendService;
import com.mrxu.server.rpc.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.mrxu.proxy.attributes.Attributes.PUSH_FAILURE_COUNT;
import static com.mrxu.proxy.attributes.Attributes.TRANSPORT;

/**
 * @author: zhaoyi.wang
 * @date: 2019-01-17 12:50
 * @description:
 */
public abstract class AbstractCallbackProcessor<T extends Command> implements CallbackProcessor {

    private Logger logger = LoggerFactory.getLogger(AbstractCallbackProcessor.class);

    private static final long timeoutMillis = 1000;

    public static final Integer maxCount = 3;

    private static final String ANDROID_CHANNEL = String.valueOf(com.mrxu.common.enums.Channel.JIXIN_ANDROID.getChannel());

    private static final String IOS_CHANNEL = String.valueOf(com.mrxu.common.enums.Channel.JIXIN_IOS.getChannel());

    private static final String PC_CHANNEL = String.valueOf(com.mrxu.common.enums.Channel.JIXIN_PC.getChannel());

    @Autowired
    private ConnectionManager connectionManager;
    @Autowired
    private MessageSendService messageSendService;

    @Override
    public void callback(Command cmd, RemotingCommand remotingCommand)
            throws ConnectionInvalidException, ConnectionNotWritableException {
        logger.debug("call back msg {}", JSON.toJSON(remotingCommand));
        String toUserId = cmd.getUserId();
        List<Connection> connections;
        // 如果接受者和发送者是同一个人的话,不对当前连接发送
        if (Objects.equals(cmd.getSenderId(), toUserId)) {
            connections = connectionManager.getOtherChannelConn(toUserId, remotingCommand.getChannel());
        } else {
            connections = connectionManager.get(toUserId);
        }
        sendCommand(remotingCommand, toUserId, connections, cmd);
    }

    public void successResponseHandler(ImResponseCommand response) {
    }

    private void retryUntilExceedRetryTimes(long msgId, String toUserId, Connection connection, RemotingCommand remotingCommand, Channel channel, int retryTimes, int connNum) {
        if (!channel.isActive()) {
            Connection newConnection = connectionManager.get(toUserId, connection.getBizChannel());
            if (newConnection != null) {
                return;
            }
        }
        InvokeFuture future = connection.getInvokeFuture(msgId);
        if (future == null) {
            return;
        }
        if (future.increRetryTimes() < retryTimes) {
            channel.writeAndFlush(remotingCommand);
            Channel finalChannel = channel;
            TimerHolder.getTimer().newTimeout((timeout1) -> {
                retryUntilExceedRetryTimes(msgId, toUserId, connection, remotingCommand, finalChannel, retryTimes, connNum);
            }, timeoutMillis, TimeUnit.MILLISECONDS);
        } else {
            String sessionId = connection.getSession().getSessionId();
            connection.removeInvokeFuture(msgId);
            Integer times = channel.attr(PUSH_FAILURE_COUNT).get();
            boolean success = channel.attr(PUSH_FAILURE_COUNT).compareAndSet(times, times + 1);
            int failureConnCount = connectionManager.incFailureCount(msgId);
            connectionManager.remove(connection);
            // 所有通道都失败， 发送到失败队列
            if (failureConnCount == connNum) {
                logger.info("retryUntilExceedRetryTimes, cmd: {}, times: {}, {}", JSON.toJSON(remotingCommand), times, success);
                messageSendService.asyncSendOfflineMessage(sessionId, remotingCommand);
                pushOfflineMessage(remotingCommand);
            } else {
                if (StringUtils.equalsAny(connection.getBizChannel(), ANDROID_CHANNEL, IOS_CHANNEL)) {
                    pushOfflineMessage(remotingCommand);
                }
                if (StringUtils.equals(connection.getBizChannel(),PC_CHANNEL)) {
                    logger.info("retry failed,record offset,cmd:{}",JSON.toJSON(remotingCommand));
                    ImRequestCommand imRequestCommand = (ImRequestCommand) remotingCommand;
                    messageSendService.asyncSendOfflineMsgOffsetMessage(imRequestCommand.getRequestObject().getUserId(),remotingCommand);
                }
            }
        }
    }

    private void retryWithoutFuture(String toUserId, Connection connection, RemotingCommand remotingCommand, int retryTimes, int currentTime) {
        Connection newConnection = connectionManager.get(toUserId, connection.getBizChannel());
        Channel channel = newConnection.getChannel();
        if (channel.isActive()) {
            channel.writeAndFlush(remotingCommand).addListener(future -> {
                if (future.isSuccess()) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Send msg done! Id={}, to remoteAddr={}",
                                remotingCommand.getId(), RemotingUtil.parseRemoteAddress(channel));
                    }
                    logger.info("Send msg done! {}", JSON.toJSON(remotingCommand));
                } else {
                    logger.error("Send msg failed! Id=" + remotingCommand.getId() + ", to remoteAddr=" + RemotingUtil.parseRemoteAddress(channel), future.cause());
                }
            });
        } else {
            if (currentTime < retryTimes) {
                TimerHolder.getTimer().newTimeout((timeout1) -> {
                    retryWithoutFuture(toUserId, connection, remotingCommand, retryTimes, currentTime + 1);
                }, timeoutMillis, TimeUnit.MILLISECONDS);
            } else {
                String sessionId = connection.getSession().getSessionId();
                connection.removeInvokeFuture(remotingCommand.getId());
                logger.info("retryWithoutFuture, cmd: {}", JSON.toJSON(remotingCommand));
                messageSendService.asyncSendOfflineMessage(sessionId, remotingCommand);
                pushOfflineMessage(remotingCommand);
            }
        }
    }

    @Override
    public void dispatch2Sender(Command cmd, RemotingCommand remotingCommand) throws ConnectionInvalidException, ConnectionNotWritableException {
        logger.debug("dispatch msg to sender {}", JSON.toJSON(remotingCommand));
        String toUserId = cmd.getSenderId();
        if (toUserId == null) {
            return;
        }
        List<Connection> connections = connectionManager.getOtherChannelConn(toUserId, remotingCommand.getChannel());
        // 推送给sender时,如果没有连接则不需要推送,也不需要做同步
        if (CollectionUtils.isNotEmpty(connections)) {
            sendCommand(remotingCommand, toUserId, connections, cmd);
        } else {
            //如果没有连接，则需要记录一条offset
            logger.debug("推送给sender时没有连接,toUserId:{},remotingCommand:{}",toUserId,remotingCommand);
            messageSendService.asyncSendOfflineMsgOffsetMessage(toUserId,remotingCommand);
        }
    }

    private void sendCommand(RemotingCommand remotingCommand, String toUserId, List<Connection> connections, Command cmd) throws ConnectionInvalidException {
        if (CollectionUtils.isEmpty(connections)) {
            messageSendService.asyncSendOfflineMsgOffsetMessage(toUserId,remotingCommand);
            if (needOfflinePush(remotingCommand)) {
                ImRequestCommand imRequestCommand = (ImRequestCommand) remotingCommand;
                messageSendService.asyncSendOfflinePushMessage(imRequestCommand.getRequestObject().getUserId(), imRequestCommand);
                return;
            } else {
                throw new ConnectionInvalidException(remotingCommand.getId() + "Connection is null when do check!");
            }
        }
        int connNum = connections.size();
        long msgId = remotingCommand.getId();
        for (Connection connection : connections) {
            connectionManager.check(connection);
            Channel channel = connection.getChannel();
            String transport = channel.attr(TRANSPORT).get();
            if ("polling".equals(transport)) {
                if (channel.isActive()) {
                    channel.writeAndFlush(remotingCommand).addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {
                            if (future.isSuccess()) {
                                if (logger.isDebugEnabled()) {
                                    logger.debug("Send msg done! Id={}, to remoteAddr={}",
                                            msgId, RemotingUtil.parseRemoteAddress(channel));
                                }
                                logger.info("成功啦，{}", JSON.toJSON(remotingCommand));
                            } else {
                                logger.error("Send msg failed! Id={}, to remoteAddr={}", msgId,
                                        RemotingUtil.parseRemoteAddress(channel));
                                TimerHolder.getTimer().newTimeout(new TimerTask() {
                                    @Override
                                    public void run(Timeout timeout) throws Exception {
                                        retryWithoutFuture(toUserId, connection, remotingCommand, 2, 1);
                                    }
                                }, timeoutMillis, TimeUnit.MILLISECONDS);
                            }
                        }
                    });
                } else {
                    TimerHolder.getTimer().newTimeout(new TimerTask() {
                        @Override
                        public void run(Timeout timeout) throws Exception {
                            retryWithoutFuture(toUserId, connection, remotingCommand, 2, 1);
                        }
                    }, timeoutMillis, TimeUnit.MILLISECONDS);
                }
            } else {
                Integer failureCount = channel.attr(PUSH_FAILURE_COUNT).get();
                // 超过失败的重试次数
                if (failureCount != null && failureCount >= maxCount) {
                    // 增加失败的通道
                    connectionManager.incFailureCount(msgId);
                    logger.info("超过失败的重试次数, failureCount: {}, 关闭链接，remotingCommand, {}"
                            , failureCount
                            , JSON.toJSON(remotingCommand));
                    connectionManager.remove(connection);
                    // 超过重试次数之后，将消息存入未读消息队列
                    messageSendService.asyncSendOfflineMessage(toUserId, remotingCommand);
                    pushOfflineMessage(remotingCommand);
                } else {
                    final InvokeFuture future = new DefaultInvokeFuture(msgId, new InvokeCallbackListener() {
                        @Override
                        public void onResponse(InvokeFuture future) {
                            ImResponseCommand response;
                            try {
                                response = (ImResponseCommand) future.waitResponse(100);
                            } catch (InterruptedException e) {
                                logger.error("msg ack process error! Id={}, from remoteAddr={}", msgId,
                                        RemotingUtil.parseRemoteAddress(channel), e);
                                return;
                            }
                            if (response != null && response.getResponseStatus() == ResponseStatus.SUCCESS) {
                                if (logger.isDebugEnabled()) {
                                    logger.debug("msg ack received! Id={}, from remoteAddr={}", response.getId(),
                                            RemotingUtil.parseRemoteAddress(channel));
                                }
                                channel.attr(PUSH_FAILURE_COUNT).set(0);
                                successResponseHandler(response);
                            } else {
                                if (response == null) {
                                    logger.error("msg timeout! The address is {}",
                                            RemotingUtil.parseRemoteAddress(channel));
                                } else {
                                    logger.error("msg exception caught! Error code={}, The address is {}",
                                            response.getResponseStatus(), RemotingUtil.parseRemoteAddress(channel));
                                }
                                Integer times = channel.attr(PUSH_FAILURE_COUNT).get();
                                channel.attr(PUSH_FAILURE_COUNT).compareAndSet(times, times + 1);
                            }
                        }

                        @Override
                        public String getRemoteAddress() {
                            return channel.remoteAddress().toString();
                        }
                    }, remotingCommand.getProtocolCode());
                    connection.addInvokeFuture(future);
                    channel.writeAndFlush(remotingCommand).addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {
                            if (future.isSuccess()) {
                                if (logger.isDebugEnabled()) {
                                    logger.debug("Send msg done! Id={}, to remoteAddr={}",
                                            msgId, RemotingUtil.parseRemoteAddress(channel));
                                }
                            } else {
                                logger.error("Send msg failed! Id={}, to remoteAddr={}", msgId,
                                        RemotingUtil.parseRemoteAddress(channel));
                            }
                        }
                    });
                    TimerHolder.getTimer().newTimeout(new TimerTask() {
                        @Override
                        public void run(Timeout timeout) throws Exception {
                            retryUntilExceedRetryTimes(msgId, toUserId, connection, remotingCommand, channel, 2, connNum);
                        }
                    }, timeoutMillis, TimeUnit.MILLISECONDS);
                }
            }
        }
    }

    private void pushOfflineMessage(RemotingCommand remotingCommand) {
        ImRequestCommand imRequestCommand = (ImRequestCommand) remotingCommand;
        messageSendService.asyncSendOfflineMsgOffsetMessage(imRequestCommand.getRequestObject().getUserId(),remotingCommand);
        if (needOfflinePush(remotingCommand)) {
            logger.info("offline push for retryUntilExceedRetryTimes, command: {}", JSON.toJSONString(remotingCommand));
            messageSendService.asyncSendOfflinePushMessage(imRequestCommand.getRequestObject().getUserId(), imRequestCommand);
        }
    }

    private boolean needOfflinePush(RemotingCommand remotingCommand) {
        return StringUtils.equalsAny(remotingCommand.getCmdCode().clazz(),
                ImCommandCode.MESSAGE_REQ.clazz(),
                ImCommandCode.MESSAGE_MODIFY_REQ.clazz(),
                ImCommandCode.FUNCTIONAL_MESSAGE_REQ.clazz(),
                ImCommandCode.RTC_MESSAGE_REQ.clazz());
    }

}

