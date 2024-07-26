package com.mrxu.event.common;

/**
 * @Description: 连接建立事件
 * @author: ztowh
 * @Date: 2018-12-25 13:07
 */
public class ConnectionEstablishEvent extends Event {

    @Override
    public EventType getEventType() {
        return EventType.CONNECT_EVENT;
    }
}
