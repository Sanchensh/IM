package com.mrxu.event.common;

import lombok.Data;

/**
 * @author: zhaoyi.wang
 * @date: 2019-04-16 09:10
 * @description:
 */
@Data
public class RobotConversationCloseEvent extends Event {

    /**
     * 会话关闭类型
     * 1: 转人工
     * 2: 超时
     * 3: 用户主动退出
     * 4: 其它
     */
    private Integer type;

    @Override
    public EventType getEventType() {
        return EventType.ROBOT_CONVERSATION_CLOSE_EVENT;
    }

}
