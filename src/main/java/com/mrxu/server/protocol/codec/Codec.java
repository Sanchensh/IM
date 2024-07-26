package com.mrxu.server.protocol.codec;

import io.netty.channel.ChannelHandler;

public interface Codec {

    /**
     * Create an encoder instance.
     *
     * @return new encoder instance
     */
    ChannelHandler newEncoder();

    /**
     * Create an decoder instance.
     *
     * @return new decoder instance
     */
    ChannelHandler newDecoder();
}
