package com.mrxu.server.handler.ws;

import com.mrxu.remote.domain.RemotingCommand;
import com.mrxu.remote.serialize.SerializerManager;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;

/**
 * @Description: 转换类
 * @author: ztowh
 * @Date: 2018-12-19 13:15
 */
@ChannelHandler.Sharable
public class WsMessageEncoder extends MessageToMessageEncoder<RemotingCommand> {


    @Override
    protected void encode(ChannelHandlerContext ctx, RemotingCommand msg, List<Object> out) throws Exception {
        String serializeResponse = new String(SerializerManager.getSerializer(msg.getSerializer()).serialize(msg));
        out.add(new TextWebSocketFrame(serializeResponse));
    }
}
