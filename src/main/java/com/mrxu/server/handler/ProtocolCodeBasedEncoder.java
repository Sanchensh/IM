package com.mrxu.server.handler;

import com.mrxu.server.protocol.Protocol;
import com.mrxu.server.protocol.ProtocolManager;
import com.mrxu.server.rpc.Connection;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.Attribute;

import java.io.Serializable;

@ChannelHandler.Sharable
public class ProtocolCodeBasedEncoder extends MessageToByteEncoder<Serializable> {

    /**
     * default protocol code
     */
    protected byte defaultProtocolCode;

    public ProtocolCodeBasedEncoder(byte defaultProtocolCode) {
        super();
        this.defaultProtocolCode = defaultProtocolCode;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Serializable msg, ByteBuf out)
            throws Exception {
        Attribute<Byte> att = ctx.channel().attr(Connection.PROTOCOL);
        byte protocolCode;
        if (att == null || att.get() == null) {
            protocolCode = this.defaultProtocolCode;
        } else {
            protocolCode = att.get();
        }
        Protocol protocol = ProtocolManager.getProtocol(protocolCode);
        protocol.getEncoder().encode(ctx, msg, out);
    }

}
