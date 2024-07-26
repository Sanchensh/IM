package com.mrxu.server.protocol.codec;

import com.mrxu.remote.domain.ImCommand;
import com.mrxu.remote.domain.ProtocolByte;
import com.mrxu.remote.domain.request.HeartBeatCommand;
import com.mrxu.remote.domain.response.HeartBeatAckCommand;
import com.mrxu.remote.domain.response.ImResponseCommand;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2018/11/22 16:48
 */
@Component
public class ImEncoder implements CommandEncoder {

    private Logger logger = LoggerFactory.getLogger(ImEncoder.class);

    @Override
    public void encode(ChannelHandlerContext ctx, Serializable msg, ByteBuf out) throws Exception {

        if (msg instanceof ImCommand) {
            /**
             * proto: code for protocol
             * type: request/response/request oneway
             * cmdcode: code for remoting command
             * channel: channel
             * requestId: id of request
             * codec: code for codec
             * respstatus: response status
             * Len: length of content
             */
            ImCommand cmd = (ImCommand) msg;

            out.writeByte(ProtocolByte.IM_PROTOCOL_CODE);
            out.writeByte(cmd.getType());
            if (!(cmd instanceof HeartBeatAckCommand) && !(cmd instanceof HeartBeatCommand)) {
                out.writeShort(cmd.getCmdCode().value());
            }
            out.writeByte(cmd.getChannel());
            out.writeLong(cmd.getId());
            out.writeByte(cmd.getSerializer());
            if (cmd instanceof ImResponseCommand && !(cmd instanceof HeartBeatAckCommand)) {
                //response status
                ImResponseCommand response = (ImResponseCommand) cmd;
                out.writeShort(response.getResponseStatus().getValue());
            }
            cmd.serialize();
            out.writeInt(cmd.getBodyLength());
            if (cmd.getBodyLength() > 0) {
                out.writeBytes(cmd.getBody());
            }
        } else {
            String warnMsg = "msg type [" + msg.getClass() + "] is not subclass of RpcCommand";
            logger.warn(warnMsg);
        }
    }
}
