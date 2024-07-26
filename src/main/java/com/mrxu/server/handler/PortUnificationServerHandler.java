package com.mrxu.server.handler;

import com.mrxu.server.handler.http.PollingHandler;
import com.mrxu.server.handler.ws.MessageEncoder;
import com.mrxu.server.handler.ws.WsHandler;
import com.mrxu.server.protocol.codec.Codec;
import com.mrxu.server.rpc.ImCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 统一端口处理tcp、ws和http
 * @author: ztowh
 * @Date: 2018/11/27 14:27
 */
public class PortUnificationServerHandler extends ByteToMessageDecoder {

    private Codec codec = new ImCodec();

    // 心跳时间 90s
    int idleTime = 90;

    private AuthorizeHandler authorizeHandler;
    private MessageEncoder messageEncoder;
    private WsHandler wsHandler;
    private PollingHandler pollingHandler;
    private ExceptionHandler exceptionHandler;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 5) {
            return;
        }
        int magic1 = in.getUnsignedByte(in.readerIndex());
        int magic2 = in.getUnsignedByte(in.readerIndex() + 1);
        if (isHttp(magic1, magic2)) {
            switchToHttp(ctx);
        } else if (isTcp()) {
            switchToTcp(ctx);
        } else {
            in.clear();
            ctx.close();
        }
    }

    private static boolean isHttp(int magic1, int magic2) {
        return
                magic1 == 'G' && magic2 == 'E' || // GET
                        magic1 == 'P' && magic2 == 'O' || // POST
                        magic1 == 'P' && magic2 == 'U' || // PUT
                        magic1 == 'H' && magic2 == 'E' || // HEAD
                        magic1 == 'O' && magic2 == 'P' || // OPTIONS
                        magic1 == 'P' && magic2 == 'A' || // PATCH
                        magic1 == 'D' && magic2 == 'E' || // DELETE
                        magic1 == 'T' && magic2 == 'R' || // TRACE
                        magic1 == 'C' && magic2 == 'O';   // CONNECT
    }

    private boolean isTcp() {
        return true;
    }

    private void switchToTcp(ChannelHandlerContext ctx) {
        ChannelPipeline pipeline = ctx.pipeline();
        BizHandler bizHandler = new BizHandler();
        // tcp连接codec
        pipeline.addLast("decoder", codec.newDecoder());
        pipeline.addLast("encoder", codec.newEncoder());
        pipeline.addLast("idleStateHandler", new IdleStateHandler(idleTime, 0, 0,
                TimeUnit.SECONDS));
        pipeline.addLast("serverIdleHandler", new ServerIdleHandler());
        pipeline.addLast("handler", bizHandler);
//        pipeline.addLast("connectionEventHandler", connectionEventHandler);
        pipeline.remove(this);
    }

    private void switchToHttp(ChannelHandlerContext ctx) {
        ChannelPipeline pipeline = ctx.pipeline();
//        pipeline.addLast("logging", new LoggingHandler(LogLevel.INFO));
        pipeline.addLast("decoder", new HttpServerCodec());
        pipeline.addLast("idleStateHandler", new IdleStateHandler(idleTime, 0, 0,
                TimeUnit.SECONDS));
        pipeline.addLast("serverIdleHandler", new ServerIdleHandler());
        pipeline.addLast("aggregator", new HttpObjectAggregator(1024 * 1024));
        pipeline.addLast("authorize", authorizeHandler);
        pipeline.addLast("websocket", new WebSocketServerProtocolHandler("/ws", null, true));
        pipeline.addLast("encoder", messageEncoder);
        pipeline.addLast("wshandler", wsHandler);
        pipeline.addLast("httphandler", pollingHandler);
        pipeline.addLast("exceptionHandler", exceptionHandler);
        pipeline.remove(this);
    }

    public AuthorizeHandler getAuthorizeHandler() {
        return authorizeHandler;
    }

    public void setAuthorizeHandler(AuthorizeHandler authorizeHandler) {
        this.authorizeHandler = authorizeHandler;
    }

    public MessageEncoder getMessageEncoder() {
        return messageEncoder;
    }

    public void setMessageEncoder(MessageEncoder messageEncoder) {
        this.messageEncoder = messageEncoder;
    }

    public WsHandler getWsHandler() {
        return wsHandler;
    }

    public void setWsHandler(WsHandler wsHandler) {
        this.wsHandler = wsHandler;
    }

    public PollingHandler getPollingHandler() {
        return pollingHandler;
    }

    public void setPollingHandler(PollingHandler pollingHandler) {
        this.pollingHandler = pollingHandler;
    }

    public ExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    public PortUnificationServerHandler setExceptionHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
        return this;
    }
}
