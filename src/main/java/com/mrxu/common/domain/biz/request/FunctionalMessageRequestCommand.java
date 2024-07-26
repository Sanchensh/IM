package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;

import java.util.List;

public class FunctionalMessageRequestCommand extends AbstractCommand {

    /**
     * 消息Id
     */
    private Long msgId;

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

    private String userId;

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
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

    public FunctionalMessageRequestCommand setMsgType(Byte msgType) {
        this.msgType = msgType;
        return this;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getSenderId() {
        return senderId;
    }

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.FUNCTIONAL_MESSAGE_REQ;
    }
}
