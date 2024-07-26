package com.mrxu.common.domain.files.request;

/**
 * 提交文件上传事务请求,客户端入参
 *
 * @author feng.chuang
 * @date 2021-04-28 15:37
 */
public class CommitTransactionRequest {
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
     * 用于事务提交的事务唯一Id
     */
    private String id;

    /**
     * 文件实际第三方存储的目录id
     */
    private String directoryId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件所属的父文件夹id
     */
    private String parentId;

    /**
     * 文件md5值
     */
    private String md5;

    /**
     * 客户端实际上传文件时, 得到的响应, response body ,json字符串
     */
    private String uploadResult;

    /**
     * 回发给服务器的信息
     */
    private String feedback;

    /**
     * 2种状态，commited aborted
     */
    private String status;

    /**
     * 上传的文件的标签
     */
    private String tags;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(String directoryId) {
        this.directoryId = directoryId;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getUploadResult() {
        return uploadResult;
    }

    public void setUploadResult(String uploadResult) {
        this.uploadResult = uploadResult;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
