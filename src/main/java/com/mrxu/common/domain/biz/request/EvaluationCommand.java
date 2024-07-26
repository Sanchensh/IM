package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

import java.util.Map;

/**
 * @Description: 评价命令
 * @author: ztowh
 * @Date: 2019-01-18 15:09
 */
@Data
public class EvaluationCommand extends AbstractCommand {

    /**
     * 客户ID
     */
    private String userId;

    /**
     * 客服ID
     */
    private String csId;

    /**
     * 评价提示语
     */
    private Map<String, Object> evaluation;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.EVALUATION_REQ;
    }

    @Override
    public String getSenderId() {
        return null;
    }
}
