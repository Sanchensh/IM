package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @author: zhaoyi.wang
 **/
@Data
public class MessageOpenedRequestCommand extends AbstractCommand {

    private String userId;

    private String senderId;

    private String receiverId;

    private boolean group;

    private Long msgId;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.MESSAGE_OPENED_REQ;
    }

}
