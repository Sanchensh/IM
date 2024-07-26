package com.mrxu.remote.converter;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.request.InQueueCommand;
import com.mrxu.event.common.EventType;
import com.mrxu.event.common.InQueueEvent;
import com.mrxu.remote.domain.RemotingCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: zhaoyi.wang
 * @date: 2019-01-14 09:13
 * @description:
 */
@Slf4j
@Component
public class InQueueEventConverter implements EventConverter<InQueueEvent, InQueueCommand> {
    @Override
    public InQueueEvent convert2Event(byte channel, InQueueCommand cmd) {
        return null;
    }

    @Override
    public InQueueCommand convert2EventCommand(InQueueEvent event) {
        return null;
    }

//    @Override
//    public RemotingCommand convert2remotingCommand(InQueueCommand cmd) {
//        return null;
//    }

    @Override
    public RemotingCommand convert2remotingCommand(InQueueEvent event) {
        return null;
    }

    @Override
    public EventType getEventType() {
        return EventType.IN_QUEUE_EVENT;
    }

    @Override
    public ImCommandCode getImCommandCode() {
        return ImCommandCode.INQUEUE_REQ;
    }
}
