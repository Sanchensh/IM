package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

import java.util.Date;

/**
 * @Description: 会话超时事件（客户端连接并未关闭，仅转AI）
 * @author: ztowh
 * @Date: 2018-12-25 13:34
 */
@Data
public class ConversationCloseCommand extends AbstractCommand {

    /**
     * 客户ID
     */
    private String userId;

    /**
     * 客服ID
     */
    private String csId;

    private Date eventTime;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.CONVERSATION_TIMEOUT_EVENT;
    }

    @Override
    public String getSenderId() {
        return null;
    }

}
