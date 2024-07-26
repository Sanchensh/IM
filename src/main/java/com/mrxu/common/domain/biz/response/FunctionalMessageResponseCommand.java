package com.mrxu.common.domain.biz.response;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;

public class FunctionalMessageResponseCommand extends AbstractCommand {
    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.FUNCTIONAL_MESSAGE_RES;
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
