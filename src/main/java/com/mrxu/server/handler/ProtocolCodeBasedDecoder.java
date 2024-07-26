package com.mrxu.server.handler;

import com.mrxu.server.protocol.Protocol;
import com.mrxu.server.protocol.ProtocolManager;
import com.mrxu.server.rpc.Connection;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ProtocolCodeBasedDecoder extends AbstractBatchDecoder {
    /**
     * by default, suggest design a single byte for protocol version.
     */
    public static final int DEFAULT_PROTOCOL_VERSION_LENGTH = 1;
    /**
     * protocol version should be a positive number, we use -1 to represent illegal
     */
    public static final int DEFAULT_ILLEGAL_PROTOCOL_VERSION_LENGTH = -1;

    private Logger logger = LoggerFactory.getLogger(ProtocolCodeBasedDecoder.class);


    /**
     * decode the protocol code
     *
     * @param in input byte buf
     * @return an instance of ProtocolCode
     */
    protected Byte decodeProtocolCode(ByteBuf in) {
        if (in.readableBytes() >= 1) {
            return in.readByte();
        }
        return null;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        in.markReaderIndex();
        Byte protocolCode = decodeProtocolCode(in);
        if (null != protocolCode) {
            if (ctx.channel().attr(Connection.PROTOCOL).get() == null) {
                ctx.channel().attr(Connection.PROTOCOL).set(protocolCode);
            }
            Protocol protocol = ProtocolManager.getProtocol(protocolCode);
            if (null != protocol) {
                in.resetReaderIndex();
                protocol.getDecoder().decode(ctx, in, out);
            } else {
                // 不识别关闭
                ctx.close();
                logger.error("未知的protocol code：{}", protocolCode);
            }
        }
    }
}
