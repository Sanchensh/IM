package com.mrxu.common.domain.biz.response;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @author: zhaoyi.wang
 * @date: 2019-03-13 10:01
 * @description: 评价结果ack
 */
@Data
public class EvaluationResultResponseCommand extends AbstractCommand {

    private String userId;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.EVALUATION_RESULT_RES;
    }

    @Override
    public String getSenderId() {
        return null;
    }
}
