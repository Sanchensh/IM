package com.mrxu.event.common;

/**
 * @Description: 连接超时事件
 * @author: ztowh
 * @Date: 2018-12-25 13:33
 */
public class ConnectionTimeoutEvent extends Event {

    @Override
    public EventType getEventType() {
        return EventType.CONNECT_TIMEOUT_EVENT;
    }
}
