package com.mrxu.event.interfaces.publish;


import com.mrxu.event.common.Event;
import com.mrxu.event.common.EventType;

/**
 * @author: zhaoyi.wang
 * @date: 2019-01-08 13:18
 * @description:
 */
public interface EventPublisher<T extends Event> {

    void publish(T t);

    EventType getEventType();

}
