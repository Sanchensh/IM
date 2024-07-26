package com.mrxu.event.common;

/**
 * @Description: 连接关闭事件
 * @author: ztowh
 * @Date: 2018-12-25 13:31
 */
public class ConnectionCloseEvent extends Event {

    @Override
    public EventType getEventType() {
        return EventType.CLOSE_EVENT;
    }

}
