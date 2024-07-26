package com.mrxu.server.handler;

import com.mrxu.server.protocol.Protocol;
import com.mrxu.server.protocol.ProtocolManager;
import com.mrxu.server.rpc.Connection;
import com.mrxu.server.rpc.RemotingContext;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2018/11/30 11:28
 */
@ChannelHandler.Sharable
@Component
public class BizHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        byte protocolCode = ctx.channel().attr(Connection.PROTOCOL).get();
        Protocol protocol = ProtocolManager.getProtocol(protocolCode);
        protocol.getCommandHandler().handleCommand(new RemotingContext(ctx, true), msg);
    }
}
