package com.mrxu.common.domain.biz.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class FunctionalMessageVO implements Serializable {

    private long id;

    /**
     * 消息Id
     */
    private long msgId;

    /**
     * 发送者Id
     */
    private String senderId;

    /**
     * 接收人Id集合
     */
    private List<String> receiverIds;

    /**
     * 功能消息类型(业务方自定义)
     * <p>
     * example:
     * <p>
     * 0: @功能
     * 1: ding
     * 2: 红包
     * 3: 收藏
     */
    private Byte type;

    /**
     * 消息类型
     */
    private Byte msgType;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 会话Id
     */
    private String conversationId;

    /**
     * 会话类型
     */
    private Byte chatType;

    /**
     * 群Id
     */
    private String groupId;

    /**
     * 是否发送给所有人
     */
    private boolean sendAll;

    /**
     * 消息推送级别
     * 0: 不推送
     * 1: 离线推送
     * 2: 免打扰离线推送
     * 3: 设备免打扰离线推送
     */
    private Byte level;

    /**
     * 发送时间
     */
    private long sendTime;

    /**
     * 状态
     * 0: 未读
     * 1: 已读
     */
    private Byte status;

    private Map<String, String> extraParams;

    public long getId() {
        return id;
    }

    public FunctionalMessageVO setId(long id) {
        this.id = id;
        return this;
    }

    public long getMsgId() {
        return msgId;
    }

    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public List<String> getReceiverIds() {
        return receiverIds;
    }

    public void setReceiverIds(List<String> receiverIds) {
        this.receiverIds = receiverIds;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getMsgType() {
        return msgType;
    }

    public FunctionalMessageVO setMsgType(Byte msgType) {
        this.msgType = msgType;
        return this;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public Byte getChatType() {
        return chatType;
    }

    public void setChatType(Byte chatType) {
        this.chatType = chatType;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public boolean isSendAll() {
        return sendAll;
    }

    public void setSendAll(boolean sendAll) {
        this.sendAll = sendAll;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public Byte getStatus() {
        return status;
    }

    public FunctionalMessageVO setStatus(Byte status) {
        this.status = status;
        return this;
    }

    public Map<String, String> getExtraParams() {
        return extraParams;
    }

    public FunctionalMessageVO setExtraParams(Map<String, String> extraParams) {
        this.extraParams = extraParams;
        return this;
    }

    @Override
    public String toString() {
        return "FunctionalMessageVO{" +
                "id=" + id +
                ", msgId=" + msgId +
                ", senderId='" + senderId + '\'' +
                ", receiverIds=" + receiverIds +
                ", type=" + type +
                ", msgType=" + msgType +
                ", content='" + content + '\'' +
                ", conversationId='" + conversationId + '\'' +
                ", chatType=" + chatType +
                ", groupId='" + groupId + '\'' +
                ", sendAll=" + sendAll +
                ", level=" + level +
                ", sendTime=" + sendTime +
                ", status=" + status +
                ", extraParams=" + extraParams +
                '}';
    }
}
