package com.mrxu.server.handler.ws;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.ParserConfig;
import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.Command;
import com.mrxu.proxy.attributes.Attributes;
import com.mrxu.remote.domain.RemotingCommand;
import com.mrxu.remote.domain.request.HeartBeatCommand;
import com.mrxu.remote.domain.request.ImRequestCommand;
import com.mrxu.remote.domain.response.ImResponseCommand;
import com.mrxu.remote.serialize.CommandCodeEnumDeser;
import com.mrxu.server.protocol.Protocol;
import com.mrxu.server.protocol.ProtocolManager;
import com.mrxu.server.rpc.CommandFactory;
import com.mrxu.server.rpc.RemotingContext;
import io.micrometer.core.instrument.Timer;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description: websocket处理器
 * @author: ztowh
 * @Date: 2018/11/27 15:52
 */
@ChannelHandler.Sharable
@Component
public class WsHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static final Logger logger = LoggerFactory.getLogger(WsHandler.class);

    @Autowired
    private CommandFactory commandFactory;

    @Autowired
    @Qualifier("requestTimer")
    private Timer requestTimer;

    static {
        ParserConfig.getGlobalInstance().putDeserializer(ImCommandCode.class, new CommandCodeEnumDeser());
//        ParserConfig.getGlobalInstance().putDeserializer(Command.class, new CommandDeserializer());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String text = msg.text();
        long startTime = System.nanoTime();
        try {
            Map<String, String> objectMap = JSON.parseObject(text, new TypeReference<Map<String, String>>() {
            });
            String type = objectMap.get("type");
            RemotingCommand cmd = null;
            if ("0".equals(type)) {
                cmd = JSON.parseObject(text, HeartBeatCommand.class);
            } else if ("1".equals(type)) {
                logger.debug("msg {}", text);
                cmd = JSON.parseObject(text, ImRequestCommand.class);
                deserializeCommand(cmd, objectMap);
            } else if ("2".equals(type)) {
                cmd = JSON.parseObject(text, ImResponseCommand.class);
                deserializeCommand(cmd, objectMap);
            } else {
                // 非正常的type直接关闭
                ctx.channel().close();
                return;
            }
            if (!ctx.channel().hasAttr(Attributes.SESSION)
                    && !ImCommandCode.AUTH_REQ.equals(cmd.getCmdCode())
                    && !ImCommandCode.REGISTER_REQ.equals(cmd.getCmdCode())) {
                ctx.channel().close();
                return;
            }
            Byte protocolCode = cmd.getProtocolCode();
            Protocol protocol = ProtocolManager.getProtocol(protocolCode);
            protocol.getCommandHandler().handleCommand(new RemotingContext(ctx, true), cmd);
        } catch (JSONException e) {
            logger.error("jsonException, msg: " + text, e);
            ctx.channel().close();
            return;
        } catch (Exception e) {
            logger.error("WsHandler exception, msg: " + text, e);
            throw e;
        } finally {
            requestTimer.record(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
        }
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
            logger.error("转换command出现异常," + JSON.toJSONString(objectMap), e);
            throw e;
        }
    }

}
