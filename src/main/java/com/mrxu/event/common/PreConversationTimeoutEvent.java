package com.mrxu.event.common;

/**
 * @Description: 会话即将超时事件(客户端发起)
 * @author: ztowh
 * @Date: 2018-12-25 13:34
 */
public class PreConversationTimeoutEvent extends Event {

    @Override
    public EventType getEventType() {
        return EventType.PRE_CONVERSATION_TIMEOUT_EVENT;
    }
}
