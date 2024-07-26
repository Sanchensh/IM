package com.mrxu.common.domain.biz.response;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @author: zhaoyi.wang
 * @description: 收取到未读消息ack响应
 **/
@Data
public class UnreadMessageAckResponseCommand extends AbstractCommand {

    /**
     * registrationId
     */
    private String userId;

    private Boolean status;

    private String errorMsg;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.UNREAD_MESSAGE_ACK_RES;
    }

    @Override
    public Boolean needCallback() {
        return false;
    }

    @Override
    public String getSenderId() {
        return null;
    }

}
