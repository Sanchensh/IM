package com.mrxu.server.handler;

import com.alibaba.fastjson.JSON;
import com.vip.vjtools.vjkit.collection.CollectionUtil;
import com.mrxu.server.AppConfigManager;
import com.mrxu.server.processor.callback.TimerHolder;
import com.mrxu.server.rpc.ConnectionManager;
import com.mrxu.server.service.TokenService;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.mrxu.proxy.attributes.Attributes.TRANSPORT;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;


/**
 * @Description: long poll类型的权限校验器
 * @Author: ztowh
 * @Date: 2019-03-13 16:14
 */
@ChannelHandler.Sharable
@Component
public class AuthorizeHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(AuthorizeHandler.class);

    // http long poll path
    private String connectPath = "/ws";

    @Autowired
    private ConnectionManager connectionManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AppConfigManager appConfigManager;

    private List<String> avaliableTransport = Arrays.asList("polling");

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest req = (FullHttpRequest) msg;
            Channel channel = ctx.channel();
            QueryStringDecoder queryDecoder = new QueryStringDecoder(req.uri());

            if (!queryDecoder.path().startsWith(connectPath)) {
                HttpResponse res = new DefaultHttpResponse(HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
                channel.writeAndFlush(res).addListener(ChannelFutureListener.CLOSE);
                req.release();
                logger.warn("Blocked wrong request! url: {}, ip: {}", queryDecoder.path(), channel.remoteAddress());
                return;
            }

            List<String> transportValue = queryDecoder.parameters().get("transport");
            if (CollectionUtil.isNotEmpty(transportValue) && !avaliableTransport.contains(transportValue.get(0))) {
                logger.warn("Got no transports for request {}", req.uri());

                HttpResponse res = new DefaultHttpResponse(HTTP_1_1, HttpResponseStatus.UNAUTHORIZED);
                channel.writeAndFlush(res).addListener(ChannelFutureListener.CLOSE);
                return;
            }

            // 仅针对long poll类型进行校验
            if (CollectionUtil.isNotEmpty(transportValue) && avaliableTransport.contains(transportValue.get(0))) {
                List<String> sid = queryDecoder.parameters().get("uid");
                List<String> token = queryDecoder.parameters().get("token");
                String uid = sid.get(0);

                if (CollectionUtil.isEmpty(sid)) {
                    HttpResponse res = new DefaultHttpResponse(HTTP_1_1, HttpResponseStatus.UNAUTHORIZED);
                    channel.writeAndFlush(res).addListener(ChannelFutureListener.CLOSE);
                    req.release();
                    return;
                }

                // 判断userid是否存在
                if (!tokenService.touchSession(uid)) {
                    HttpResponse res = new DefaultHttpResponse(HTTP_1_1, HttpResponseStatus.UNAUTHORIZED);
                    channel.writeAndFlush(res).addListener(ChannelFutureListener.CLOSE);
                    req.release();
                    return;
                }

                ctx.channel().attr(TRANSPORT).set("polling");
                // 仅针对get请求更新当前的session
                if (HttpMethod.GET.equals(req.method())) {
                    saveSession(ctx, channel, uid, queryDecoder.parameters(), req);
                }
            }
        } else {
            logger.debug("not FullHttpRequest, msg: {}", JSON.toJSON(msg));
        }
        ctx.fireChannelRead(msg);
    }

    private void saveSession(ChannelHandlerContext ctx, Channel channel, String uid, Map<String, List<String>> params,
                             FullHttpRequest req) {
        Map<String, List<String>> headers = new HashMap<String, List<String>>(req.headers().names().size());
        for (String name : req.headers().names()) {
            List<String> values = req.headers().getAll(name);
            headers.put(name, values);
        }

        List<String> conversationId = params.get("conversationId");
        List<String> imChannel = params.get("channel");
        Map<String, Object> extraMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(conversationId)) {
            extraMap.put("conversationId", conversationId.get(0));
        }
        if (CollectionUtil.isNotEmpty(imChannel)) {
            extraMap.put("channel", imChannel.get(0));
        } else {
            extraMap.put("channel", "");
        }

        if (params.containsKey("connect")) {
            connectionManager.add(uid, (String) extraMap.get("channel"), ctx, extraMap);
        } else {
            connectionManager.bind(uid, (String) extraMap.get("channel"), ctx, extraMap);
        }
        // 长时间没有发送请求，即认为会话关闭，因为长轮询没有状态，有更好的方法可以替换？
        TimerHolder.schedule(uid, () -> {
            connectionManager.remove(uid, (String) extraMap.get("channel"), ctx);
        }, appConfigManager.getTimeout() + appConfigManager.getInterval(), TimeUnit.MILLISECONDS);
    }
}
