package com.mrxu.remote.converter;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.request.InQueueTimeoutResultCommand;
import com.mrxu.event.common.EventType;
import com.mrxu.event.common.InQueueTimeoutResultEvent;
import com.mrxu.remote.domain.RemotingCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: zhaoyi.wang
 * @date: 2019-02-18 14:49
 * @description:
 */
@Slf4j
@Component
public class InQueueTimeoutResultEventConverter implements EventConverter<InQueueTimeoutResultEvent, InQueueTimeoutResultCommand> {

    @Override
    public InQueueTimeoutResultEvent convert2Event(byte channel, InQueueTimeoutResultCommand cmd) {
        InQueueTimeoutResultEvent event = new InQueueTimeoutResultEvent();
        event.setUserId(cmd.getUserId());
        event.setConversationId(cmd.getConversationId());
        event.setEventTime(new Date());
        event.setWait(cmd.getWait());
        event.setExtraParams(cmd.getExtraParams());
        event.setChannel(channel);
        return event;
    }

    @Override
    public InQueueTimeoutResultCommand convert2EventCommand(InQueueTimeoutResultEvent event) {
        return null;
    }

    @Override
    public RemotingCommand convert2remotingCommand(InQueueTimeoutResultEvent event) {
        return null;
    }

    @Override
    public EventType getEventType() {
        return EventType.IN_QUEUE_TIMEOUT_RESULT_EVENT;
    }

    @Override
    public ImCommandCode getImCommandCode() {
        return ImCommandCode.IN_QUEUE_TIMEOUT_RESULT;
    }
}
