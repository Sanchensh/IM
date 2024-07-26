package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @author: zhaoyi.wang
 * @date: 2019-02-21 17:44
 * @description:
 */
@Data
public class InQueueTimeoutRequestCommand extends AbstractCommand {

    private String userId;

    /**
     * 排队超时提示语
     */
    private String content;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.IN_QUEUE_TIMEOUT_REQ;
    }

    @Override
    public String getSenderId() {
        return null;
    }
}
