package com.mrxu.server.handler.ws;

import com.mrxu.common.exception.SerializationException;
import com.mrxu.proxy.session.Session;
import com.mrxu.remote.domain.RemotingCommand;
import com.mrxu.remote.serialize.SerializerManager;
import com.mrxu.server.AppConfigManager;
import com.mrxu.server.OptionMessage;
import com.mrxu.server.handler.PacketEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.mrxu.proxy.attributes.Attributes.*;
import static com.mrxu.proxy.attributes.Attributes.ORIGIN;
import static com.mrxu.proxy.attributes.Attributes.USER_AGENT;
import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpHeaderValues.APPLICATION_JSON;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @Description: 参考socket io
 * @Author: ztowh
 * @Date: 2019-03-20 15:50
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class MessageEncoder extends ChannelOutboundHandlerAdapter {

    private PacketEncoder encoder = new PacketEncoder();

    @Autowired
    private AppConfigManager appConfigManager;

    private void sendMessage(Channel channel, RemotingCommand cmd, ByteBuf out, String type, ChannelPromise promise) {
        HttpResponse res = new DefaultHttpResponse(HTTP_1_1, HttpResponseStatus.OK);
        Session session = channel.attr(SESSION).get();
        res.headers().add(CONTENT_TYPE, type).add(CONNECTION, KEEP_ALIVE);

        if (session != null) {
            res.headers().add("Set-Cookie", "io=" + session.getSessionId());
        }

        addOriginHeaders(channel, res, cmd);
        HttpUtil.setContentLength(res, out.readableBytes());

        // prevent XSS warnings on IE
        // https://github.com/LearnBoost/socket.io/pull/1333
        String userAgent = channel.attr(USER_AGENT).get();
        if (userAgent != null && (userAgent.contains(";MSIE") || userAgent.contains("Trident/"))) {
            res.headers().add("X-XSS-Protection", "0");
        }

        sendMessage(channel, out, res, promise);
    }

    private void sendMessage(Channel channel, ByteBuf out, HttpResponse res, ChannelPromise promise) {
        channel.write(res);

        if (out.isReadable()) {
            channel.write(new DefaultHttpContent(out));
        } else {
            out.release();
        }

        channel.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT, promise).addListener(ChannelFutureListener.CLOSE);
    }

    private void addOriginHeaders(Channel channel, HttpResponse res, RemotingCommand cmd) {

        if (StringUtils.isNotEmpty(appConfigManager.getOrigin())) {
            res.headers().add(ACCESS_CONTROL_ALLOW_ORIGIN, appConfigManager.getOrigin());
            res.headers().add(ACCESS_CONTROL_ALLOW_CREDENTIALS, Boolean.TRUE);
        } else {
            String origin = channel.attr(ORIGIN).get();
            if (origin != null) {
                res.headers().add(ACCESS_CONTROL_ALLOW_ORIGIN, origin);
                res.headers().add(ACCESS_CONTROL_ALLOW_CREDENTIALS, Boolean.TRUE);
            } else {
                res.headers().add(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            }
        }
        // Option请求需要添加额外请求头
        if (cmd instanceof OptionMessage) {
            res.headers().add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_HEADERS, HttpHeaderNames.CONTENT_TYPE);
        }
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (!(msg instanceof RemotingCommand)) {
            super.write(ctx, msg, promise);
            return;
        }
        String transport = ctx.channel().attr(TRANSPORT).get();
        if ("polling".equals(transport)) {
            handleHTTP(ctx, (RemotingCommand) msg, promise);
        } else {
            handleWebsocket(ctx, (RemotingCommand) msg, promise);
        }
    }

    private void handleWebsocket(ChannelHandlerContext ctx, RemotingCommand msg, ChannelPromise promise) throws SerializationException {
        String serializeResponse = new String(SerializerManager.getSerializer(msg.getSerializer()).serialize(msg));
        WebSocketFrame res = new TextWebSocketFrame(serializeResponse);
        ctx.channel().writeAndFlush(res, promise);
    }

    private void handleHTTP(ChannelHandlerContext ctx, RemotingCommand msg, ChannelPromise promise) throws SerializationException, IOException {
        Channel channel = ctx.channel();
        ByteBuf out = encoder.allocateBuffer(ctx.alloc());
        ByteBufOutputStream outputStream = new ByteBufOutputStream(out);
        byte[] bytes = SerializerManager.getSerializer(msg.getSerializer()).serialize(msg);
        outputStream.write(bytes);
        sendMessage(channel, msg, out, APPLICATION_JSON.toString(), promise);
    }

}
