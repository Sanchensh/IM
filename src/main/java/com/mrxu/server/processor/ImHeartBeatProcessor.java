package com.mrxu.server.processor;

import com.mrxu.common.utils.RemotingUtil;
import com.mrxu.proxy.attributes.Attributes;
import com.mrxu.proxy.session.Session;
import com.mrxu.proxy.session.SessionManager;
import com.mrxu.remote.domain.RemotingCommand;
import com.mrxu.remote.domain.request.HeartBeatCommand;
import com.mrxu.remote.domain.response.HeartBeatAckCommand;
import com.mrxu.server.rpc.Connection;
import com.mrxu.server.rpc.InvokeFuture;
import com.mrxu.server.rpc.RemotingContext;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2018/11/30 15:55
 */
public class ImHeartBeatProcessor extends AbstractRemotingProcessor<RemotingCommand> {

    private Logger logger = LoggerFactory.getLogger(ImHeartBeatProcessor.class);

    private SessionManager sessionManager;

    @Override
    public void doProcess(RemotingContext ctx, RemotingCommand msg) throws Exception {
        if (msg instanceof HeartBeatCommand) {// process the heartbeat
            final long id = msg.getId();
            if (logger.isDebugEnabled()) {
                logger.debug("Heartbeat received! Id=" + id + ", from "
                        + RemotingUtil.parseRemoteAddress(ctx.getChannelContext().channel()));
            }
            // 更新session的redis expire时间
            Session session = ctx.getChannelContext().channel().attr(Attributes.SESSION).get();
//            if (session != null) {
//                String sessionId = session.getSessionId();
//                if (StringUtils.isNotBlank(sessionId)) {
//                    sessionManager.touchSession(sessionId);
//                }
//            }
            HeartBeatAckCommand ack = new HeartBeatAckCommand();
            ack.setId(id);
            ctx.getChannelContext().channel().writeAndFlush(ack).addListener(new ChannelFutureListener() {

                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("Send heartbeat ack done! Id={}, to remoteAddr={}", id,
                                    RemotingUtil.parseRemoteAddress(ctx.getChannelContext().channel()));
                        }
                    } else {
                        logger.error("Send heartbeat ack failed! Id={}, to remoteAddr={}", id,
                                RemotingUtil.parseRemoteAddress(ctx.getChannelContext().channel()));
                    }
                }

            });
        } else if (msg instanceof HeartBeatAckCommand) {
            Connection conn = ctx.getChannelContext().channel().attr(Connection.CONNECTION).get();
            InvokeFuture future = conn.removeInvokeFuture(msg.getId());
            if (future != null) {
                future.putResponse(msg);
                try {
                    future.executeInvokeCallback();
                } catch (Exception e) {
                    logger.error(
                            "Exception caught when executing heartbeat invoke callback. From {}",
                            RemotingUtil.parseRemoteAddress(ctx.getChannelContext().channel()), e);
                }
            } else {
                logger.warn("Cannot find heartbeat InvokeFuture, maybe already timeout. Id={}, From {}",
                                msg.getId(),
                                RemotingUtil.parseRemoteAddress(ctx.getChannelContext().channel()));
            }
        } else {
            throw new RuntimeException("Cannot process command: " + msg.getClass().getName());
        }
    }

    public ImHeartBeatProcessor() {
    }

    public ImHeartBeatProcessor(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

}
