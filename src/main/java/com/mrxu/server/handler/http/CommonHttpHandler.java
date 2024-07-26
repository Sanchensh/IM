package com.mrxu.server.handler.http;

import com.vip.vjtools.vjkit.text.TextValidator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.rtsp.RtspHeaderNames;
import io.netty.util.ReferenceCountUtil;
import org.springframework.stereotype.Component;

import static io.netty.buffer.Unpooled.copiedBuffer;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2018/11/28 15:18
 */
@ChannelHandler.Sharable
@Component
public class CommonHttpHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if (msg instanceof FullHttpRequest) {
                FullHttpRequest httpRequest = (FullHttpRequest) msg;
                String host = httpRequest.headers().get(RtspHeaderNames.HOST).split(":")[0];
                if (TextValidator.isIp(host) || "localhost".equals(host)) {
                    FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST, copiedBuffer(HttpResponseStatus.BAD_REQUEST.reasonPhrase().getBytes()));
                    response.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN);
                    response.headers().set(HttpHeaderNames.CONTENT_LENGTH, HttpResponseStatus.BAD_REQUEST.reasonPhrase().length());
                    ctx.writeAndFlush(response);
                    return;
                } else {
                    final String responseMessage = "move on";
                    FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, copiedBuffer(responseMessage.getBytes()));
                    response.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN);
                    response.headers().set(HttpHeaderNames.CONTENT_LENGTH, responseMessage.length());
                    ctx.writeAndFlush(response);
                }
                return;
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }

        super.channelRead(ctx, msg);
    }
}
