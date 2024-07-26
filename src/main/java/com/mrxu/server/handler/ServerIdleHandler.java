package com.mrxu.server.handler;

import com.mrxu.common.utils.RemotingUtil;
import com.mrxu.proxy.attributes.Attributes;
import com.mrxu.proxy.session.Session;
import com.mrxu.server.rpc.ConnectionManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@ChannelHandler.Sharable
@Component
public class ServerIdleHandler extends ChannelDuplexHandler {

    private Logger logger = LoggerFactory.getLogger(ServerIdleHandler.class);

    @Autowired
    private ConnectionManager connectionManager;

    /**
     * @see io.netty.channel.ChannelInboundHandlerAdapter#userEventTriggered(ChannelHandlerContext, Object)
     */
    @Override
    public void userEventTriggered(final ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            try {
                String sessionId = null;
                Channel channel = ctx.channel();
                if (channel.hasAttr(Attributes.SESSION)) {
                    Session session = channel.attr(Attributes.SESSION).get();
                    if (session != null) {
                        sessionId = session.getSessionId();
                    }
                }
                logger.warn("Connection idle, sessionId: {}, close it from server side: {}", sessionId,
                        RemotingUtil.parseRemoteAddress(ctx.channel()));
                ctx.close();
            } catch (Exception e) {
                logger.warn("Exception caught when closing connection in ServerIdleHandler.", e);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
