package com.mrxu.common.domain.files.response;

import java.util.Map;

/**
 * 创建上传事务的响应
 *
 * @author feng.chuang
 * @date 2021-04-28 15:10
 */
public class CreateTransactionResponse {
    /**
     * 上传事务的唯一标识
     */
    private String id;


    /**
     * 文件上传的实际第三方存储的父文件夹Id
     */
    private String directoryId;

    /**
     * 存储类型，s3,ks3,默认是s3
     */
    private String storage;

    /**
     * 上传请求序列,包含用于上传的请求信息
     */
    private Map<Object,Object> uploadRequests;

    /**
     * 文件内容hash,统一采用md5
     */
    private Map<String,Object> fileHashes;

    /**
     * 调用方回传给服务器的信息
     */
    private String feedback;

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

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public Map<Object, Object> getUploadRequests() {
        return uploadRequests;
    }

    public void setUploadRequests(Map<Object, Object> uploadRequests) {
        this.uploadRequests = uploadRequests;
    }

    public Map<String, Object> getFileHashes() {
        return fileHashes;
    }

    public void setFileHashes(Map<String, Object> fileHashes) {
        this.fileHashes = fileHashes;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
