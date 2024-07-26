package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @author: zhaoyi.wang
 * @date: 2019-03-01 15:32
 * @description:
 */
@Data
public class FirstResponseCommand extends AbstractCommand {

    private String userId;

    /**
     * 首响方
     * 0：客户
     * 1：客服
     */
    private byte side;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.FIRST_RESPONSE;
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
