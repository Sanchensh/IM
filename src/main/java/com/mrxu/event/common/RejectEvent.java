package com.mrxu.event.common;

/**
 * @author: zhaoyi.wang
 * @date: 2019-01-14 09:18
 * @description:
 */
public class RejectEvent extends Event {
    @Override
    public EventType getEventType() {
        return EventType.REJECT_EVENT;
    }
}
