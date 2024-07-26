package com.mrxu.event.common;

import lombok.Data;

/**
 * @author: zhaoyi.wang
 * @date: 2019-01-14 09:17
 * @description:
 */
@Data
public class AssignEvent extends Event {

    /**
     * 分配的客服ID
     */
    private String csId;

    @Override
    public EventType getEventType() {
        return EventType.ASSIGN_EVENT;
    }
}
