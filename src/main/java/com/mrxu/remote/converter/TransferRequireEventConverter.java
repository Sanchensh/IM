package com.mrxu.remote.converter;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.request.TransferRequireCommand;
import com.mrxu.event.common.EventType;
import com.mrxu.event.common.TransferRequireEvent;
import com.mrxu.remote.domain.RemotingCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: zhaoyi.wang
 * @date: 2019-01-10 13:25
 * @description:
 */
@Slf4j
@Component
public class TransferRequireEventConverter implements EventConverter<TransferRequireEvent, TransferRequireCommand> {
    @Override
    public TransferRequireEvent convert2Event(byte channel, TransferRequireCommand cmd) {
        TransferRequireEvent event = new TransferRequireEvent();
        event.setUserId(cmd.getUserId());
        event.setConversationId(cmd.getConversationId());
        event.setEventTime(new Date());
        event.setChannel(channel);
        event.setExtraParams(cmd.getExtraParams());
        return event;
    }

    @Override
    public TransferRequireCommand convert2EventCommand(TransferRequireEvent event) {
        return null;
    }

//    @Override
//    public RemotingCommand convert2remotingCommand(TransferRequireCommand cmd) {
//        return null;
//    }

    @Override
    public RemotingCommand convert2remotingCommand(TransferRequireEvent event) {
        return null;
    }

    @Override
    public EventType getEventType() {
        return EventType.TRANSFER_REQUIRE_EVENT;
    }

    @Override
    public ImCommandCode getImCommandCode() {
        return ImCommandCode.TRANSFER_REQUIRE_EVENT;
    }
}
