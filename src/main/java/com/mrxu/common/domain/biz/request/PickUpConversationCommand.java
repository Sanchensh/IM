package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @author: zhaoyi.wang
 * @date: 2019-03-04 13:01
 * @description: 接起会话命令
 */
@Data
public class PickUpConversationCommand extends AbstractCommand {

    /**
     * 客服ID
     */
    private String userId;

    /**
     * 客户ID
     */
    private String customerId;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.PICK_UP_CONVERSATION;
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
