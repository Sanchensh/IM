package com.mrxu.remote.converter;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.request.ConnectionCloseCommand;
import com.mrxu.event.common.ConnectionCloseEvent;
import com.mrxu.event.common.EventType;
import com.mrxu.remote.domain.RemotingCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: zhaoyi.wang
 * @date: 2019-01-08 14:23
 * @description:
 */
@Slf4j
@Component
public class ConnectionCloseEventConverter implements EventConverter<ConnectionCloseEvent, ConnectionCloseCommand> {

    @Override
    public ConnectionCloseEvent convert2Event(byte channel, ConnectionCloseCommand cmd) {
        ConnectionCloseEvent event = new ConnectionCloseEvent();
        event.setUserId(cmd.getUserId());
        event.setConversationId(cmd.getConversationId());
        event.setEventTime(new Date());
        event.setChannel(channel);
        event.setExtraParams(cmd.getExtraParams());
        return event;
    }

    @Override
    public ConnectionCloseCommand convert2EventCommand(ConnectionCloseEvent event) {
        if (event == null) {
            return null;
        }
        ConnectionCloseCommand cmd = new ConnectionCloseCommand();
        cmd.setUserId(event.getUserId());
        cmd.setEventTime(event.getEventTime());
        cmd.setConversationId(event.getConversationId());
        cmd.setExtraParams(event.getExtraParams());
        return cmd;
    }

//    @Override
//    public RemotingCommand convert2remotingCommand(ConnectionCloseCommand cmd) {
//        return null;
//    }

    @Override
    public RemotingCommand convert2remotingCommand(ConnectionCloseEvent event) {
        if (event == null) {
            return null;
        }
        ConnectionCloseCommand cmd = convert2EventCommand(event);
        return convert2remotingCommand(event.getChannel(), cmd);
    }

    @Override
    public EventType getEventType() {
        return EventType.CLOSE_EVENT;
    }

    @Override
    public ImCommandCode getImCommandCode() {
        return ImCommandCode.CLOSE_EVENT;
    }
}
