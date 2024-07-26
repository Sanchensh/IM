package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

import java.util.Date;

/**
 * @author: zhaoyi.wang
 * @date: 2019-04-16 08:56
 * @description: 机器人会话关闭事件
 */
@Data
public class RobotConversationCloseCommand extends AbstractCommand {

    /**
     * 客户ID
     */
    private String userId;

    /**
     * 会话关闭类型
     * 1: 转人工
     * 2: 超时
     * 3: 用户主动退出
     * 4: 其它
     */
    private Integer type;

    private Date eventTime;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.ROBOT_CONVERSATION_CLOSE;
    }

    @Override
    public String getSenderId() {
        return null;
    }
}
