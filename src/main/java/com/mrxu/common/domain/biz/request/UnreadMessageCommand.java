package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @author: zhaoyi.wang
 * @description: 拉取未读消息请求
 **/
@Data
public class UnreadMessageCommand extends AbstractCommand {

    /**
     * registrationId
     */
    private String userId;

    private String senderId;

    private Long msgId;

    /**
     * 每次获取消息数量, default: 100
     */
    private Integer msgSize;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.UNREAD_MESSAGE_REQ;
    }
    
}
