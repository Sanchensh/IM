package com.mrxu.event.common;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @Description: 事件
 * @author: ztowh
 * @Date: 2018-12-25 10:01
 */
@Data
public abstract class Event implements Serializable {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 会话ID
     */
    private String conversationId;

    /**
     * 渠道
     */
    private byte channel;

    /**
     * 事件时间
     */
    private Date eventTime;

    private Map<String, Object> extraParams;

    public abstract EventType getEventType();
}
