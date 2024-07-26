package com.mrxu.event.common;

import lombok.Data;

/**
 * @author: zhaoyi.wang
 * @date: 2019-02-18 14:41
 * @description: 排队超时，客户端返回是否继续等待结果
 */
@Data
public class InQueueTimeoutResultEvent extends Event {

    // 1： continue wait，otherwise： quit wait
    private short wait;

    @Override
    public EventType getEventType() {
        return EventType.IN_QUEUE_TIMEOUT_RESULT_EVENT;
    }
}
