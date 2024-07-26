package com.mrxu.common.domain.files.request;

/**
 * 文件转发请求参数
 *
 * @author feng.chuang
 * @date 2021-05-06 13:56
 */
public class FileForwardRequest {
    /**
     * 消息发送者
     */
    private String senderId;

    /**
     * 文件转发的接收者
     */
    private String toReceiverId;

    /**
     * 要转发的会话的类型(0-单聊，1-群聊，2-公众号)
     */
    private Byte toChatType;

    /**
     * 资源唯一Id
     */
    private String resourceId;

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getToReceiverId() {
        return toReceiverId;
    }

    public void setToReceiverId(String toReceiverId) {
        this.toReceiverId = toReceiverId;
    }

    public Byte getToChatType() {
        return toChatType;
    }

    public void setToChatType(Byte toChatType) {
        this.toChatType = toChatType;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
}
