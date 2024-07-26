package com.mrxu.server.processor;

import com.alibaba.fastjson.JSON;
import com.mrxu.common.utils.RemotingUtil;
import com.mrxu.mq.ZMSTemplate;
import com.mrxu.remote.ZimConfiguration;
import com.mrxu.remote.domain.RemotingCommand;
import com.mrxu.remote.domain.response.ImResponseCommand;
import com.mrxu.server.rpc.Connection;
import com.mrxu.server.rpc.InvokeFuture;
import com.mrxu.server.rpc.RemotingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * @Description: 接收响应处理器，处理超时任务
 * @author: ztowh
 * @Date: 2018/11/30 15:55
 */
public class ImResponseProcessor extends AbstractRemotingProcessor<RemotingCommand> {

    private static final Logger logger = LoggerFactory.getLogger(ImResponseProcessor.class);

    private static final String BAOHE_RESPONSE_TOPIC = "tf_zim_response_baohe";
    private static final String CUSTOMER_SERVICE_RESPONSE_TOPIC = "tf_zim_response_customer_service";

    private ZMSTemplate zmsTemplate;

    private ZimConfiguration zimConfiguration;

    public ImResponseProcessor(ZMSTemplate zmsTemplate, ZimConfiguration zimConfiguration) {
        this.zmsTemplate = zmsTemplate;
        this.zimConfiguration = zimConfiguration;
    }

    @Override
    public void doProcess(RemotingContext ctx, RemotingCommand cmd) throws Exception {
        Connection conn = ctx.getChannelContext().channel().attr(Connection.CONNECTION).get();
        InvokeFuture future = conn.removeInvokeFuture(cmd.getId());
        ClassLoader oldClassLoader = null;
        try {
            if (future != null) {
                if (future.getAppClassLoader() != null) {
                    oldClassLoader = Thread.currentThread().getContextClassLoader();
                    Thread.currentThread().setContextClassLoader(future.getAppClassLoader());
                }
                future.putResponse(cmd);
                try {
                    future.executeInvokeCallback();
                } catch (Exception e) {
                    logger.error("Exception caught when executing invoke callback, id={}",
                            cmd.getId(), e);
                }
            } else {
                logger.warn("Cannot find InvokeFuture, maybe already timeout, id={}, from={} ",
                        cmd.getId(),
                        RemotingUtil.parseRemoteAddress(ctx.getChannelContext().channel()));
            }
        } finally {
            if (null != oldClassLoader) {
                Thread.currentThread().setContextClassLoader(oldClassLoader);
            }
        }
        try {
            if (cmd instanceof ImResponseCommand) {
                ImResponseCommand responseCommand = (ImResponseCommand) cmd;
                if (responseCommand.getResponseTimeMillis() <= 0) {
                    responseCommand.setResponseTimeMillis(System.currentTimeMillis());
                }
                if (Objects.equals("im", zimConfiguration.getRegistry())) {
                    zmsTemplate.send(BAOHE_RESPONSE_TOPIC, String.valueOf(responseCommand.getId()), JSON.toJSONString(cmd));
                } else {
                    zmsTemplate.send(CUSTOMER_SERVICE_RESPONSE_TOPIC, String.valueOf(responseCommand.getId()), JSON.toJSONString(cmd));
                }
            }
        } catch (Exception e) {
            logger.error("send response to baohe/cs error, biz: " + cmd.getBiz(), e);
        }
    }
}
