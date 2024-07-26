package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @author: zhaoyi.wang
 * @date: 2019-01-16 14:30
 * @description:
 */
@Data
public class QueueNumRequestCommand extends AbstractCommand {

    private String userId;

    /*
     * 技能组id
     */
    private String serviceGroupId;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.QUEUE_NUM_REQ;
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
