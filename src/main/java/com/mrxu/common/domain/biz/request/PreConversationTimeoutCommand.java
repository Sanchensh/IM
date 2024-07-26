package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

import java.util.Date;

/**
 * @Description: 会话即将超时事件
 * @author: ztowh
 * @Date: 2018-12-25 13:34
 */
@Data
public class PreConversationTimeoutCommand extends AbstractCommand {

    /*
     * 客户的id
     */
    private String userId;

    private Date eventTime;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.PRE_CONVERSATION_TIMEOUT_EVENT;
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
