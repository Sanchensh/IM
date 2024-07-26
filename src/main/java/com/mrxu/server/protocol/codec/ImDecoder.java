package com.mrxu.server.protocol.codec;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.ImCommandType;
import com.mrxu.common.ResponseStatus;
import com.mrxu.remote.domain.ProtocolByte;
import com.mrxu.remote.domain.request.AbstractRequestCommand;
import com.mrxu.remote.domain.request.HeartBeatCommand;
import com.mrxu.remote.domain.request.ImRequestCommand;
import com.mrxu.remote.domain.response.ImResponseCommand;
import com.mrxu.server.protocol.ImProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2018/11/22 15:23
 */
@Component
public class ImDecoder implements CommandDecoder {

    private Logger logger = LoggerFactory.getLogger(ImDecoder.class);

    private int lessLength;

    {
        lessLength = ImProtocol.getResponseHeaderLength() < ImProtocol.getRequestHeaderLength() ? ImProtocol
                .getResponseHeaderLength() : ImProtocol.getRequestHeaderLength();
    }

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        if (in.readableBytes() > lessLength) {
            in.markReaderIndex();

            byte protocol = in.readByte();
            in.resetReaderIndex();
            if (protocol == ProtocolByte.IM_PROTOCOL_CODE) {
                if (in.readableBytes() > 2) {
                    in.markReaderIndex();
                    in.readByte();
                    byte type = in.readByte(); //type
                    if (type == ImCommandType.REQUEST) {
                        if (in.readableBytes() > ImProtocol.getRequestHeaderLength() - 2) {
                            short cmdCode = in.readShort();
                            byte channel = in.readByte();
                            long requestId = in.readLong();
                            byte serializer = in.readByte();
                            int length = in.readInt();
                            byte[] content = null;
                            if (in.readableBytes() >= length) {
                                if (length > 0) {
                                    content = new byte[length];
                                    in.readBytes(content);
                                }
                            } else {
                                in.resetReaderIndex();
                                return;
                            }
                            AbstractRequestCommand command = createRequestCommand(cmdCode);
                            command.setType(type);
                            command.setChannel(channel);
                            command.setId(requestId);
                            command.setSerializer(serializer);
                            command.setBody(content);

                            out.add(command);
                        } else {
                            in.resetReaderIndex();
                        }
                    } else if (type == ImCommandType.RESPONSE) {
                        if (in.readableBytes() > ImProtocol.getResponseHeaderLength() - 2) {
                            short cmdCode = in.readShort();
                            byte channel = in.readByte();
                            long requestId = in.readLong();
                            byte serializer = in.readByte();
                            short status = in.readShort();
                            int length = in.readInt();
                            byte[] content = null;
                            if (in.readableBytes() >= length) {
                                if (length > 0) {
                                    content = new byte[length];
                                    in.readBytes(content);
                                }
                            } else {
                                in.resetReaderIndex();
                                return;
                            }
                            ImResponseCommand command = createResponseCommand(cmdCode);
                            command.setType(type);
                            command.setChannel(channel);
                            command.setId(requestId);
                            command.setSerializer(serializer);
                            command.setBody(content);
                            command.setResponseStatus(ResponseStatus.valueOf(status));
                            command.setResponseTimeMillis(System.currentTimeMillis());
                            out.add(command);
                        } else {
                            in.resetReaderIndex();
                        }
                    } else if (type == ImCommandType.HEARTBEAT) {
                        if (in.readableBytes() > ImProtocol.getRequestHeaderLength() - 2) {
                            byte channel = in.readByte();
                            long requestId = in.readLong();
                            byte serializer = in.readByte();
                            int length = in.readInt();
                            byte[] content = null;
                            if (in.readableBytes() >= length) {
                                if (length > 0) {
                                    content = new byte[length];
                                    in.readBytes(content);
                                }
                            } else {
                                in.resetReaderIndex();
                                return;
                            }
                            AbstractRequestCommand command = new HeartBeatCommand();
                            command.setType(type);
                            command.setChannel(channel);
                            command.setId(requestId);
                            command.setSerializer(serializer);
                            command.setBody(content);

                            out.add(command);
                        } else {
                            in.resetReaderIndex();
                        }
                    } else {
                        String emsg = "Unknown command type: " + type;
                        logger.error(emsg);
                        throw new RuntimeException(emsg);
                    }
                }
            } else {
                String emsg = "Unknown protocol: " + protocol;
                logger.error(emsg);
                throw new RuntimeException(emsg);
            }
        }
    }

    private ImResponseCommand createResponseCommand(short cmdCode) {
        ImResponseCommand command = new ImResponseCommand();
        command.setCmdCode(ImCommandCode.valueOf(cmdCode));
        return command;
    }

    private ImRequestCommand createRequestCommand(short cmdCode) {
        ImRequestCommand command = new ImRequestCommand();
        command.setCmdCode(ImCommandCode.valueOf(cmdCode));
        command.setArriveTime(System.currentTimeMillis());
        return command;
    }
}
