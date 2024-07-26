package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @author: zhaoyi.wang
 * @date: 2019-02-18 16:30
 * @description:
 */
@Data
public class InQueueTimeoutResultCommand extends AbstractCommand {

    private String userId;

    /**
     * 是否继续等待排队，1： 继续等待
     */
    private short wait;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.IN_QUEUE_TIMEOUT_RESULT;
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
