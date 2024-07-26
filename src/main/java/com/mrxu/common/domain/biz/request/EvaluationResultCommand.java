package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @Description: 评价结果
 * @author: ztowh
 * @Date: 2019-01-21 15:59
 */
@Data
public class EvaluationResultCommand extends AbstractCommand {

    private String userId;

    private String csId;

    private Integer result;

    private String resultMsg;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.EVALUATION_RESULT;
    }

    @Override
    public String getSenderId() {
        return null;
    }

//    @Override
//    public Boolean needCallback() {
//        return false;
//    }
}
