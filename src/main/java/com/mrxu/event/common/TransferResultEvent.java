package com.mrxu.event.common;

import lombok.Data;

/**
 * @Description: 客服互转请求（客服转接后，用户端sdk响应时触发）
 * @author: ztowh
 * @Date: 2018-12-25 13:33
 */
@Data
public class TransferResultEvent extends Event {


    /**
     * 1: 转接人
     * 2: 转接组
     */
    private Integer type;

    /**
     * 接受请求的客服ID
     */
    private String csId;

    /**
     * 技能组ID
     */
    private String groupId;

    @Override
    public EventType getEventType() {
        return EventType.TRANSFER_RESULT_EVENT;
    }

}
