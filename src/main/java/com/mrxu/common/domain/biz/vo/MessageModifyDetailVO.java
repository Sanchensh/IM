package com.mrxu.common.domain.biz.vo;

import java.io.Serializable;
import java.util.Map;

/**
 * @author: zhaoyi.wang
 * @data 2021/4/9 10:34 上午
 * @description:
 */
public class MessageModifyDetailVO implements Serializable {

    private String type;

    private String content;

    private long sendTime;

    private Map<String, Object> extraParams;

    public String getType() {
        return type;
    }

    public MessageModifyDetailVO setType(String type) {
        this.type = type;
        return this;
    }

    public String getContent() {
        return content;
    }

    public MessageModifyDetailVO setContent(String content) {
        this.content = content;
        return this;
    }

    public long getSendTime() {
        return sendTime;
    }

    public MessageModifyDetailVO setSendTime(long sendTime) {
        this.sendTime = sendTime;
        return this;
    }

    public Map<String, Object> getExtraParams() {
        return extraParams;
    }

    public MessageModifyDetailVO setExtraParams(Map<String, Object> extraParams) {
        this.extraParams = extraParams;
        return this;
    }

    @Override
    public String toString() {
        return "MessageModifyDetailVO{" +
                "type=" + type +
                ", content='" + content + '\'' +
                ", sendTime=" + sendTime +
                ", extraParams=" + extraParams +
                '}';
    }
}
