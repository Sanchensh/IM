package com.mrxu.event.interfaces.service;


import com.mrxu.event.common.Event;

/**
 * @author: zhaoyi.wang
 * @date: 2019-01-08 13:19
 * @description:
 */
public interface EventPublisherService {

    void publish(Event event);

}
