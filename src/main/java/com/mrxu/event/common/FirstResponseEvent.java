package com.mrxu.event.common;

import lombok.Data;

/**
 * @author: zhaoyi.wang
 * @date: 2019-03-01 14:27
 * @description: 首响事件
 */
@Data
public class FirstResponseEvent extends Event {

    @Override
    public EventType getEventType() {
        return EventType.FIRST_RESPONSE_EVENT;
    }

}
