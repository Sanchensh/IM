package com.mrxu.event.common;

/**
 * @author: zhaoyi.wang
 * @date: 2019-01-14 09:14
 * @description:
 */
public class InQueueEvent extends Event {
    @Override
    public EventType getEventType() {
        return EventType.IN_QUEUE_EVENT;
    }
}
