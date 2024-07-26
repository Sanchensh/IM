package com.mrxu.server.handler.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.Command;
import com.mrxu.remote.domain.RemotingCommand;
import com.mrxu.remote.domain.request.HeartBeatCommand;
import com.mrxu.remote.domain.request.ImRequestCommand;
import com.mrxu.remote.domain.response.ImResponseCommand;
import com.mrxu.server.OptionMessage;
import com.mrxu.server.protocol.Protocol;
import com.mrxu.server.protocol.ProtocolManager;
import com.mrxu.server.rpc.RemotingContext;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.mrxu.proxy.attributes.Attributes.ORIGIN;
import static com.mrxu.proxy.attributes.Attributes.USER_AGENT;
import static io.netty.buffer.Unpooled.copiedBuffer;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @Description:
 * @Author: ztowh
 * @Date: 2019-03-22 18:25
 */
@Component
@ChannelHandler.Sharable
@Slf4j
public class PollingHandler extends ChannelInboundHandlerAdapter {

    public static final String NAME = "polling";
    public static final String STATUS = "/ws/status";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest req = (FullHttpRequest) msg;
            QueryStringDecoder queryDecoder = new QueryStringDecoder(req.uri());

            List<String> transport = queryDecoder.parameters().get("transport");

            if (transport != null && NAME.equals(transport.get(0))) {

                String origin = req.headers().get(HttpHeaderNames.ORIGIN);
                ctx.channel().attr(ORIGIN).set(origin);

                String userAgent = req.headers().get(HttpHeaderNames.USER_AGENT);
                ctx.channel().attr(USER_AGENT).set(userAgent);

                try {
                    handleMessage(req, queryDecoder, ctx);
                } finally {
                    req.release();
                }
                return;
            }
            // check status
            if (STATUS.equals(req.uri())) {
                final String responseMessage = "ok";
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, copiedBuffer(responseMessage.getBytes()));
                response.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN);
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, responseMessage.length());
                ctx.writeAndFlush(response);
                req.release();
                return;
            }
        }
        ctx.fireChannelRead(msg);
    }

    private void handleMessage(FullHttpRequest req, QueryStringDecoder queryDecoder, ChannelHandlerContext ctx)
            throws Exception {
        String origin = req.headers().get(HttpHeaderNames.ORIGIN);
        if (HttpMethod.POST.equals(req.method())) {
            onPost(ctx, origin, req.content());
        } else if (HttpMethod.GET.equals(req.method())) {
            // do nothing
            onGet(ctx, origin);
        } else if (HttpMethod.OPTIONS.equals(req.method())) {
            onOptions(ctx, origin);
        } else {
            log.error("Wrong {} method invocation for {}", req.method());
            sendError(ctx);
        }
    }

    private void onOptions(ChannelHandlerContext ctx, String origin) {
        ctx.writeAndFlush(new OptionMessage()).addListener(ChannelFutureListener.CLOSE);
    }

    private void onPost(ChannelHandlerContext ctx, String origin, ByteBuf content) throws Exception {
        int endIndex = content.bytesBefore((byte) '|');
        if (endIndex <= 0) {
            return;
        }
//		int i = content.bytesBefore(endIndex, (byte) ',');
//		if( i <= 0){
//			return ;
//		}
        String type = readString(content, endIndex);
        content.skipBytes(1);
        ByteBufInputStream in = new ByteBufInputStream(content);
        Map<String, String> objectMap = JSON.parseObject(in, new TypeReference<Map<String, String>>() {
        }.getType());
        try {
            RemotingCommand cmd;
            if ("0".equals(type)) {
                cmd = JSON.parseObject(in, HeartBeatCommand.class);
            } else if ("1".equals(type)) {
                cmd = JSON.parseObject(in, ImRequestCommand.class);
                deserializeCommand(cmd, objectMap);
            } else if ("2".equals(type)) {
                cmd = JSON.parseObject(in, ImResponseCommand.class);
                deserializeCommand(cmd, objectMap);
            } else {
                ctx.channel().close();
                return;
            }
            Byte protocolCode = cmd.getProtocolCode();
            Protocol protocol = ProtocolManager.getProtocol(protocolCode);
            protocol.getCommandHandler().handleCommand(new RemotingContext(ctx, true), cmd);
        } catch (JSONException e) {
            log.error("jsonException, msg: " + JSON.toJSONString(objectMap), e);
            ctx.channel().close();
            return;
        } catch (Exception e) {
            log.error("WsHandler exception, msg: " + JSON.toJSONString(objectMap), e);
            throw e;
        }

    }

    private String readString(ByteBuf frame, int size) {
        byte[] bytes = new byte[size];
        frame.readBytes(bytes);
        return new String(bytes, CharsetUtil.UTF_8);
    }

    protected void onGet(ChannelHandlerContext ctx, String origin) {
    }

    private void sendError(ChannelHandlerContext ctx) {
        HttpResponse res = new DefaultHttpResponse(HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        ctx.channel().writeAndFlush(res).addListener(ChannelFutureListener.CLOSE);
    }

    private void deserializeCommand(RemotingCommand cmd, Map<String, String> objectMap) throws Exception {
        try {
            if (cmd instanceof ImRequestCommand) {
                ImRequestCommand requestCommand = (ImRequestCommand) cmd;
                ImCommandCode cmdCode = requestCommand.getCmdCode();
                Class<?> clazz = Class.forName(cmdCode.clazz());
                String requestObject = MapUtils.getString(objectMap, "requestObject");
                Command command = (Command) JSON.parseObject(requestObject, clazz);
                requestCommand.setRequestObject(command);
            } else if (cmd instanceof ImResponseCommand) {
                ImResponseCommand responseCommand = (ImResponseCommand) cmd;
                ImCommandCode cmdCode = responseCommand.getCmdCode();
                Class<?> clazz = Class.forName(cmdCode.clazz());
                String responseObject = MapUtils.getString(objectMap, "responseObject");
                Command command = (Command) JSON.parseObject(responseObject, clazz);
                responseCommand.setResponseObject(command);
            }
        } catch (Exception e) {
            log.error("转换command出现异常," + JSON.toJSONString(objectMap), e);
            throw e;
        }
    }


}
