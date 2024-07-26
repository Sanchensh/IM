package com.mrxu.common.domain.biz.response;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;

/**
 * @author: zhaoyi.wang
 * @data 2020/11/30 9:10 上午
 * @description:
 */
public class DeleteMessageResponseCommand extends AbstractCommand {

    @Override
    public String getUserId() {
        return null;
    }

    @Override
    public String getSenderId() {
        return null;
    }

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.DELETE_MESSAGE_RES;
    }

}
