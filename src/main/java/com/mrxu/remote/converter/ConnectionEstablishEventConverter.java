package com.mrxu.remote.converter;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.request.ConnectionEstablishCommand;
import com.mrxu.event.common.ConnectionEstablishEvent;
import com.mrxu.event.common.EventType;
import com.mrxu.remote.domain.RemotingCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: zhaoyi.wang
 * @date: 2019-01-04 15:26
 * @description:
 */
@Slf4j
@Component
public class ConnectionEstablishEventConverter implements EventConverter<ConnectionEstablishEvent, ConnectionEstablishCommand> {

    @Override
    public ConnectionEstablishEvent convert2Event(byte channel, ConnectionEstablishCommand cmd) {
        ConnectionEstablishEvent event = new ConnectionEstablishEvent();
        event.setUserId(cmd.getUserId());
        event.setConversationId(cmd.getConversationId());
        event.setEventTime(new Date());
        event.setChannel(channel);
        event.setExtraParams(cmd.getExtraParams());
        return event;
    }

    @Override
    public ConnectionEstablishCommand convert2EventCommand(ConnectionEstablishEvent event) {
        if (event == null) {
            return null;
        }
        ConnectionEstablishCommand cmd = new ConnectionEstablishCommand();
        cmd.setUserId(event.getUserId());
        cmd.setEventTime(event.getEventTime());
        cmd.setConversationId(event.getConversationId());
        cmd.setExtraParams(event.getExtraParams());
        return cmd;
    }

    @Override
    public RemotingCommand convert2remotingCommand(ConnectionEstablishEvent event) {
        if (event == null) {
            return null;
        }
        ConnectionEstablishCommand cmd = convert2EventCommand(event);
        return convert2remotingCommand(event.getChannel(), cmd);
    }

    @Override
    public EventType getEventType() {
        return EventType.CONNECT_EVENT;
    }

    @Override
    public ImCommandCode getImCommandCode() {
        return ImCommandCode.CONNECT_EVENT;
    }

}
