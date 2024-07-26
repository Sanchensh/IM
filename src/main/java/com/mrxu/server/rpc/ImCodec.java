package com.mrxu.server.rpc;

import com.mrxu.remote.domain.ProtocolByte;
import com.mrxu.server.handler.ProtocolCodeBasedDecoder;
import com.mrxu.server.handler.ProtocolCodeBasedEncoder;
import com.mrxu.server.protocol.codec.Codec;
import io.netty.channel.ChannelHandler;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2018/11/23 14:14
 */
public class ImCodec implements Codec {

    @Override
    public ChannelHandler newEncoder() {
        return new ProtocolCodeBasedEncoder(ProtocolByte.IM_PROTOCOL_CODE);
    }

    @Override
    public ChannelHandler newDecoder() {
        return new ProtocolCodeBasedDecoder();
    }
}
