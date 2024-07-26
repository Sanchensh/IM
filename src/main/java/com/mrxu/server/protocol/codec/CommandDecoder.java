package com.mrxu.server.protocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public interface CommandDecoder {
    /**
     * Decode bytes into object.
     *
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */
    void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception;
}
