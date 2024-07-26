package com.mrxu.event.common;

/**
 * @Description: 会话超时事件（客户端连接并未关闭，仅转AI）
 * @author: ztowh
 * @Date: 2018-12-25 13:34
 */
public class ConversationTimeoutEvent extends Event {

    @Override
    public EventType getEventType() {
        return EventType.CONVERSATION_TIMEOUT_EVENT;
    }
}
