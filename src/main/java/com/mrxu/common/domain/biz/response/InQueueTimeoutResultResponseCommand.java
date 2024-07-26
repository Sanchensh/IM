package com.mrxu.common.domain.biz.response;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @author: zhaoyi.wang
 * @date: 2019-03-13 10:02
 * @description: 排队超时结果ack
 */
@Data
public class InQueueTimeoutResultResponseCommand extends AbstractCommand {

    private String userId;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.IN_QUEUE_TIMEOUT_RESULT_RES;
    }

    @Override
    public String getSenderId() {
        return null;
    }

}