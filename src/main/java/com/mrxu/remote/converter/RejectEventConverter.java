package com.mrxu.remote.converter;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.request.RejectCommand;
import com.mrxu.event.common.EventType;
import com.mrxu.event.common.RejectEvent;
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
public class RejectEventConverter implements EventConverter<RejectEvent, RejectCommand> {

    @Override
    public RejectEvent convert2Event(byte channel, RejectCommand cmd) {
        return null;
    }

    @Override
    public RejectCommand convert2EventCommand(RejectEvent event) {
        return null;
    }

//    @Override
//    public RemotingCommand convert2remotingCommand(RejectCommand cmd) {
//        return null;
//    }

    @Override
    public RemotingCommand convert2remotingCommand(RejectEvent event) {
        return null;
    }

    @Override
    public EventType getEventType() {
        return EventType.REJECT_EVENT;
    }

    @Override
    public ImCommandCode getImCommandCode() {
        return ImCommandCode.REJECT_REQ;
    }
}
