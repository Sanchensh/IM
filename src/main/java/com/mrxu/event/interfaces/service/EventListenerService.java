package com.mrxu.event.interfaces.service;

import com.mrxu.common.domain.biz.Command;
import com.mrxu.event.common.Event;

/**
 * @author: zhaoyi.wang
 * @date: 2019-01-03 15:40
 * @description:
 */
public interface EventListenerService {

    Command listener(Event event);

}
