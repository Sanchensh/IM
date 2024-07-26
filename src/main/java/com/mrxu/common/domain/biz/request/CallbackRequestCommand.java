package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @author: zhaoyi.wang
 * @description: 客服回呼命令
 **/
@Data
public class CallbackRequestCommand extends AbstractCommand {

    private String senderId;

    private String receiverId;

    private String platform;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.CALLBACK_REQ;
    }

    @Override
    public String getUserId() {
        return this.receiverId;
    }

    @Override
    public String getSenderId() {
        return senderId;
    }
}
