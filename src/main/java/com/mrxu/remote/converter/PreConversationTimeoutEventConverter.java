package com.mrxu.remote.converter;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.request.PreConversationTimeoutCommand;
import com.mrxu.event.common.EventType;
import com.mrxu.event.common.PreConversationTimeoutEvent;
import com.mrxu.remote.domain.RemotingCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: zhaoyi.wang
 * @date: 2019-01-07 13:11
 * @description:
 */
@Slf4j
@Component
public class PreConversationTimeoutEventConverter implements EventConverter<PreConversationTimeoutEvent, PreConversationTimeoutCommand> {

    @Override
    public PreConversationTimeoutEvent convert2Event(byte channel, PreConversationTimeoutCommand cmd) {
        PreConversationTimeoutEvent event = new PreConversationTimeoutEvent();
        event.setUserId(cmd.getUserId());
        event.setConversationId(cmd.getConversationId());
        event.setEventTime(new Date());
        event.setChannel(channel);
        event.setExtraParams(cmd.getExtraParams());
        return event;
    }

    @Override
    public PreConversationTimeoutCommand convert2EventCommand(PreConversationTimeoutEvent event) {
        return null;
    }

//    @Override
//    public RemotingCommand convert2remotingCommand(PreConversationTimeoutCommand cmd) {
//        return null;
//    }

    @Override
    public RemotingCommand convert2remotingCommand(PreConversationTimeoutEvent event) {
        return null;
    }

    @Override
    public EventType getEventType() {
        return EventType.PRE_CONVERSATION_TIMEOUT_EVENT;
    }

    @Override
    public ImCommandCode getImCommandCode() {
        return ImCommandCode.PRE_CONVERSATION_TIMEOUT_EVENT;
    }
}
