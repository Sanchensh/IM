package com.mrxu.event.common;

import lombok.Data;

/**
 * @author: zhaoyi.wang
 * @date: 2019-03-04 13:08
 * @description:
 */
@Data
public class PickUpConversationEvent extends Event {

    /**
     * 客户ID
     */
    private String customerId;

    @Override
    public EventType getEventType() {
        return EventType.PICK_UP_CONVERSATION_EVENT;
    }
}
