package com.mrxu.common.domain.biz;

import java.util.Map;

/**
 * @Description: 抽象command类
 * @author: ztowh
 * @Date: 2018-12-29 14:09
 */
public abstract class AbstractCommand implements Command {

    /*
     * 会话id
     */
    private String conversationId;

    private Map<String, Object> extraParams;

    @Override
    public Boolean needCallback() {
        return true;
    }

    public Map<String, Object> getExtraParams() {
        return extraParams;
    }

    public void setExtraParams(Map<String, Object> extraParams) {
        this.extraParams = extraParams;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
}
