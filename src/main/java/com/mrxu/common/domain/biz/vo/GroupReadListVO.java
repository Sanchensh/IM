package com.mrxu.common.domain.biz.vo;

import java.io.Serializable;

/**
 * @author: zhaoyi.wang
 * @description:
 **/
public class GroupReadListVO implements Serializable {

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 最大消息Id
     */
    private Long msgId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public GroupReadListVO() {
    }

    public GroupReadListVO(String userId, Long msgId) {
        this.userId = userId;
        this.msgId = msgId;
    }

}
