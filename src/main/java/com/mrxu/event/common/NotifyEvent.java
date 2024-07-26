package com.mrxu.event.common;

import lombok.Data;

/**
 * @author: zhaoyi.wang
 * @date: 2019-01-14 09:18
 * @description:
 */
@Data
public class NotifyEvent extends Event {

    private String msg;

    private String msgType;

    @Override
    public EventType getEventType() {
        return EventType.NOTIFY_EVENT;
    }
}
