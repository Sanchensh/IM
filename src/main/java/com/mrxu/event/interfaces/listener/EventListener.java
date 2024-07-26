package com.mrxu.event.interfaces.listener;

import com.mrxu.common.domain.biz.Command;
import com.mrxu.event.common.Event;
import com.mrxu.event.common.EventType;


/**
 * @author: zhaoyi.wang
 * @date: 2019-01-03 15:41
 * @description:
 */
public interface EventListener<T extends Event> {

    Command onEvent(T event);

    EventType getEventType();

}
