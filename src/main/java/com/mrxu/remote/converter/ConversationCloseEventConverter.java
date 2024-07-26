package com.mrxu.remote.converter;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.request.ConversationCloseCommand;
import com.mrxu.event.common.ConversationTimeoutEvent;
import com.mrxu.event.common.EventType;
import com.mrxu.remote.domain.RemotingCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: zhaoyi.wang
 * @date: 2019-01-07 10:05
 * @description:
 */
@Slf4j
@Component
public class ConversationCloseEventConverter implements EventConverter<ConversationTimeoutEvent, ConversationCloseCommand> {

    @Override
    public ConversationTimeoutEvent convert2Event(byte channel, ConversationCloseCommand cmd) {
        ConversationTimeoutEvent event = new ConversationTimeoutEvent();
        event.setUserId(cmd.getUserId());
        event.setConversationId(cmd.getConversationId());
        event.setEventTime(new Date());
        event.setChannel(channel);
        event.setExtraParams(cmd.getExtraParams());
        return event;
    }

    @Override
    public ConversationCloseCommand convert2EventCommand(ConversationTimeoutEvent event) {
        return null;
    }

//    @Override
//    public RemotingCommand convert2remotingCommand(ConversationTimeoutCommand cmd) {
//        return null;
//    }

    @Override
    public RemotingCommand convert2remotingCommand(ConversationTimeoutEvent event) {
        return null;
    }


    @Override
    public EventType getEventType() {
        return EventType.CONVERSATION_TIMEOUT_EVENT;
    }

    @Override
    public ImCommandCode getImCommandCode() {
        return ImCommandCode.CONVERSATION_TIMEOUT_EVENT;
    }

}
