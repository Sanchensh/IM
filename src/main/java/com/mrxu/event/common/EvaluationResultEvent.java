package com.mrxu.event.common;

import lombok.Data;

/**
 * @author: zhaoyi.wang
 * @date: 2019-02-19 09:11
 * @description:
 */
@Data
public class EvaluationResultEvent extends Event {

    /**
     * 客服ID
     */
    private String csId;

    /**
     * 评价等级
     */
    private Integer result;

    /**
     * 评价内容
     */
    private String resultMsg;

    @Override
    public EventType getEventType() {
        return EventType.EVALUATION_RESULT_EVENT;
    }
}
