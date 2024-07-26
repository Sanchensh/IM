package com.mrxu.common.domain.files.request;

public class DeleteFileRequest {
    /**
     * 消息发送者
     */
    private String senderId;

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

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
}
