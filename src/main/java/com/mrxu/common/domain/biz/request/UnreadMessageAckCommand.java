package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

import java.util.List;

/**
 * @author: zhaoyi.wang
 * @description: 收到未读消息ack
 **/
@Data
public class UnreadMessageAckCommand extends AbstractCommand {

    /**
     * registrationId
     */
    private String userId;

    private String senderId;

    /**
     * 消息ID集合
     */
    private List<String> msgIds;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.UNREAD_MESSAGE_ACK;
    }

    @Override
    public Boolean needCallback() {
        return false;
    }

}
