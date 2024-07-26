package com.mrxu.remote.converter;
import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.request.AssignCommand;
import com.mrxu.event.common.AssignEvent;
import com.mrxu.event.common.EventType;
import com.mrxu.remote.domain.RemotingCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: zhaoyi.wang
 * @date: 2019-01-14 09:20
 * @description:
 */
@Slf4j
@Component
public class AssignEventConverter implements EventConverter<AssignEvent, AssignCommand> {
    @Override
    public AssignEvent convert2Event(byte channel, AssignCommand cmd) {
        return null;
    }

    @Override
    public AssignCommand convert2EventCommand(AssignEvent event) {
        return null;
    }

//    @Override
//    public RemotingCommand convert2remotingCommand(AssignCommand cmd) {
//        return null;
//    }

    @Override
    public RemotingCommand convert2remotingCommand(AssignEvent event) {
        return null;
    }

    @Override
    public EventType getEventType() {
        return EventType.ASSIGN_EVENT;
    }

    @Override
    public ImCommandCode getImCommandCode() {
        return ImCommandCode.ASSIGN_REQ;
    }
}
