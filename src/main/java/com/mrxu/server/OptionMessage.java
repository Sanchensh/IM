package com.mrxu.server;


import com.mrxu.common.ImCommandCode;
import com.mrxu.common.exception.DeserializationException;
import com.mrxu.common.exception.SerializationException;
import com.mrxu.remote.domain.RemotingCommand;

public class OptionMessage implements RemotingCommand {
    @Override
    public byte getProtocolCode() {
        return 0;
    }

    @Override
    public byte getChannel() {
        return 0;
    }

    @Override
    public String getBiz() {
        return null;
    }

    @Override
    public ImCommandCode getCmdCode() {
        return null;
    }

    @Override
    public byte getType() {
        return 0;
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public byte getSerializer() {
        return 0;
    }

    @Override
    public void serialize() throws SerializationException {

    }

    @Override
    public void deserialize() throws DeserializationException {

    }
}
