package com.mrxu.common.domain.files.request;

/**
 * 文件上传事务请求对象，客户端入参
 *
 * @author feng.chuang
 * @date 2021-04-28 14:37
 */
public class CreateTransactionRequest {
    /**
     * 发送者ID
     */
    private String senderId;

    /**
     * 接收者ID
     */
    private String receiverId;

    /**
     * 会话类型，单聊-0，群聊-1，公众号-2
     */
    private Byte chatType;

    /**
     * 要上传的文件名称
     */
    private String fileName;

    /**
     * 要上传的文件大小
     */
    private Long fileSize;

    /**
     * 文件md5值
     */
    private String md5;

    /**
     * 上传方式，post,put,不传默认post
     */
    private String uploadMethod;

    /**
     * 上传文件的父文件夹ID，不传默认为会话空间根目录
     */
    private String parentId;

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public Byte getChatType() {
        return chatType;
    }

    public void setChatType(Byte chatType) {
        this.chatType = chatType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getUploadMethod() {
        return uploadMethod;
    }

    public void setUploadMethod(String uploadMethod) {
        this.uploadMethod = uploadMethod;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
