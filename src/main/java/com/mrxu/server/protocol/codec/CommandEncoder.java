package com.mrxu.server.protocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.Serializable;

public interface CommandEncoder {

    /**
     * Encode object into bytes.
     *
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     */
    void encode(ChannelHandlerContext ctx, Serializable msg, ByteBuf out) throws Exception;

}
