package com.mrxu.common.domain.biz.response;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @author: zhaoyi.wang
 * @description:
 **/
@Data
public class CallbackResponseCommand extends AbstractCommand {

    private String userId;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.CALLBACK_RES;
    }

    @Override
    public String getSenderId() {
        return null;
    }
}
