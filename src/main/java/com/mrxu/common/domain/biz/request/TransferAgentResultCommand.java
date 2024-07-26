package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @author: zhaoyi.wang
 * @date: 2019-02-19 11:19
 * @description: 客服转客服，转接响应命令
 */
@Data
public class TransferAgentResultCommand extends AbstractCommand {

    /**
     * 客服ID
     */
    private String userId;

    /**
     * 客户ID
     */
    private String customerId;

    /**
     * 请求转接的客服ID
     */
    private String csId;

    /**
     * 技能组ID
     */
    private String groupId;

    /**
     * 是否同意转接
     * 同意：1
     * 不同意：0
     */
    private short agree;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.TRANSFER_AGENT_RESULT;
    }

    @Override
    public String getSenderId() {
        return null;
    }

}
