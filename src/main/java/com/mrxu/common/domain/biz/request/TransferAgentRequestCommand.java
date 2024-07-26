package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @author: zhaoyi.wang
 * @date: 2019-02-19 11:19
 * @description: 客服转客服，转接请求命令
 */
@Data
public class TransferAgentRequestCommand extends AbstractCommand {

    /**
     * 客服ID
     */
    private String userId;

    /**
     * 客户ID
     */
    private String customerId;

    /**
     * 1. 客服
     * 2. 技能组
     */
    private Integer type;

    /**
     * 转接客服ID
     */
    private String csId;

    /**
     * 转接技能组ID
     */
    private String groupId;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.TRANSFER_AGENT_REQ;
    }

    @Override
    public String getSenderId() {
        return null;
    }

}
