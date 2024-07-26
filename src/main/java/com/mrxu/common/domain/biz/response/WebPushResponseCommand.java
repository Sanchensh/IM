package com.mrxu.common.domain.biz.response;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

@Data
public class WebPushResponseCommand extends AbstractCommand {

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.WEB_PUSH_RES;
    }

    @Override
    public String getUserId() {
        return null;
    }

    @Override
    public String getSenderId() {
        return null;
    }
}
