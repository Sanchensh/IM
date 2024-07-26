package com.mrxu.server.protocol;

import com.mrxu.remote.domain.ProtocolByte;
import com.mrxu.server.protocol.codec.CommandDecoder;
import com.mrxu.server.protocol.codec.CommandEncoder;
import com.mrxu.server.rpc.CommandFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: proto: code for protocol
 * type: request/response
 * cmdcode: code for remoting command
 * requestId: id of request
 * codec: code for codec
 * timeout: timeout
 * Len: length of content
 * Request command protocol for v1
 * 0     1     2           4           6           8          10           12          14         16
 * +-----+-----+-----+-----+-------+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
 * |proto| type|     cmd   |channel|       requestId       |codec|       Len             |
 * +-----------+-----------+-------------+-----------+-----------+-----------+-----------+-----------+
 * |     |                                      content  bytes                                     |
 * +-----                                                                                          +
 * |                               ... ...                                                         |
 * +-----------------------------------------------------------------------------------------------+
 * <p>
 * proto: code for protocol
 * type: request/response
 * cmdcode: code for remoting command
 * requestId: id of request
 * codec: code for codec
 * respstatus: response status
 * Len: length of content
 * <p>
 * Response command protocol for v1
 * 0     1     2           4           6           8          10           12          14         16
 * +-----+-----+-----+-----+-------+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
 * |proto| type|     cmd   |channel|      requestId        |codec|respstatus |         Len           |
 * +-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
 * |                                            content  bytes                                     |
 * +                                                                                               +
 * |                               ... ...                                                         |
 * +-----------------------------------------------------------------------------------------------+
 * @author: ztowh
 * @Date: 2018/11/22 15:08
 */
@Slf4j
@Component
public class ImProtocol implements Protocol {

    private static final int REQUEST_HEADER_LEN = 14;
    private static final int RESPONSE_HEADER_LEN = 16;

    @Autowired
    private CommandEncoder encoder;
    @Autowired
    private CommandDecoder decoder;
    //    @Autowired
//    private HeartbeatTrigger heartbeatTrigger;
    @Autowired
    private CommandHandler commandHandler;
    @Autowired
    private CommandFactory commandFactory;

    public ImProtocol() {
    }

    @Override
    public CommandEncoder getEncoder() {
        return this.encoder;
    }

    @Override
    public CommandDecoder getDecoder() {
        return this.decoder;
    }

    @Override
    public CommandHandler getCommandHandler() {
        return this.commandHandler;
    }

    @Override
    public CommandFactory getCommandFactory() {
        return this.commandFactory;
    }

    /**
     * Get the length of request header.
     */
    public static int getRequestHeaderLength() {
        return ImProtocol.REQUEST_HEADER_LEN;
    }

    /**
     * Get the length of response header.
     */
    public static int getResponseHeaderLength() {
        return ImProtocol.RESPONSE_HEADER_LEN;
    }

    @Override
    public byte getProtocolCode() {
        return ProtocolByte.IM_PROTOCOL_CODE;
    }

}
