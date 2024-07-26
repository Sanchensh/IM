package com.mrxu.common.domain.biz.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

import java.io.Serializable;

/**
 * @author: zhaoyi.wang
 * @data 2021/2/24 9:41 上午
 * @description:
 */
public class ConversationTagVO implements Serializable {

    /**
     * 消息ID
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long msgId;

    /**
     * 标签类型
     * eg: 0: @, 1: ding, 2: 红包
     */
    private Byte type;

    /**
     * 是否发送所有人
     */
    private Boolean isSendAll;

    public Long getMsgId() {
        return msgId;
    }

    public ConversationTagVO setMsgId(Long msgId) {
        this.msgId = msgId;
        return this;
    }

    public Byte getType() {
        return type;
    }

    public ConversationTagVO setType(Byte type) {
        this.type = type;
        return this;
    }

    public Boolean getSendAll() {
        return isSendAll;
    }

    public ConversationTagVO setSendAll(Boolean sendAll) {
        isSendAll = sendAll;
        return this;
    }

    @Override
    public String toString() {
        return "ConversationTagVO{" +
                "msgId=" + msgId +
                ", type=" + type +
                ", isSendAll=" + isSendAll +
                '}';
    }
}
