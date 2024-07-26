package com.mrxu.remote.converter;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.Command;
import com.mrxu.event.common.Event;
import com.mrxu.event.common.EventType;
import com.mrxu.remote.domain.RemotingCommand;
import com.mrxu.remote.domain.request.ImRequestCommand;

/**
 * @author: zhaoyi.wang
 * @date: 2019-01-08 13:33
 * @description:
 */
public interface EventConverter<T extends Event, S extends Command> {

    T convert2Event(byte channel, S cmd);

    S convert2EventCommand(T event);

    //    RemotingCommand convert2remotingCommand(S cmd);
//
    RemotingCommand convert2remotingCommand(T event);

    EventType getEventType();

    ImCommandCode getImCommandCode();

    default RemotingCommand convert2remotingCommand(byte channel, Command cmd) {
        if (cmd == null) {
            return null;
        }
        return new ImRequestCommand(channel, cmd);
    }

}
