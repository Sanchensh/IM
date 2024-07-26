package com.mrxu.remote.domain;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.exception.DeserializationException;
import com.mrxu.common.exception.SerializationException;

import java.io.Serializable;

public interface RemotingCommand extends Serializable {
    /**
     * Get the code of the protocol that this command belongs to
     *
     * @return protocol code
     */
    byte getProtocolCode();

    byte getChannel();

    String getBiz();

    /**
     * Get the command code for this command
     *
     * @return command code
     */
    ImCommandCode getCmdCode();

    byte getType();

    /**
     * Get the id of the command
     *
     * @return an long value represent the command id
     */
    long getId();

    /**
     * Get serializer type for this command
     *
     * @return
     */
    byte getSerializer();


    /**
     * Serialize all parts of remoting command
     *
     * @throws SerializationException
     */
    void serialize() throws SerializationException;

    /**
     * Deserialize all parts of remoting command
     *
     * @throws DeserializationException
     */
    void deserialize() throws DeserializationException;

}
