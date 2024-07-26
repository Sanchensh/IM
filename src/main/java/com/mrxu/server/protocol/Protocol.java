package com.mrxu.server.protocol;

import com.mrxu.server.protocol.codec.CommandDecoder;
import com.mrxu.server.protocol.codec.CommandEncoder;
import com.mrxu.server.rpc.CommandFactory;

public interface Protocol {
    /**
     * Get the newEncoder for the protocol.
     *
     * @return
     */
    CommandEncoder getEncoder();

    /**
     * Get the decoder for the protocol.
     *
     * @return
     */
    CommandDecoder getDecoder();

    /**
     * Get the command handler for the protocol.
     *
     * @return
     */
    CommandHandler getCommandHandler();

    /**
     * Get the command factory for the protocol.
     *
     * @return
     */
    CommandFactory getCommandFactory();

    byte getProtocolCode();
}
