package com.mrxu.common.domain.files.request;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Map;

/**
 * 创建事务参数，第三方存储入参
 *
 * @author feng.chuang
 * @date 2021-04-26 09:55
 */
public class CreateTransactionParameter {
    /**
     *文件大小
     */
    @JSONField(name = "file_size")
    private Long fileSize;

    /**
     * 文件hash值，key有2种情况，sha1和md5
     */
    @JSONField(name = "file_hashes")
    private Map<String, Object> fileHashes;

    /**
     * 要上传的文件名
     */
    @JSONField(name = "file_name")
    private String fileName;

    /**
     * 文件名冲突行为 ，默认失败，枚举值
     * fail - 返回失败
     * rename - 重命名
     * overwrite - 覆盖
     */
    @JSONField(name = "file_name_conflict_behavior")
    private String fileNameConflictBehavior;

    /**
     * 上传请求方法 Default: PUT Enum:PUT POST
     */
    @JSONField(name = "upload_method")
    private String uploadMethod;

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Map<String, Object> getFileHashes() {
        return fileHashes;
    }

    public void setFileHashes(Map<String, Object> fileHashes) {
        this.fileHashes = fileHashes;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileNameConflictBehavior() {
        return fileNameConflictBehavior;
    }

    public void setFileNameConflictBehavior(String fileNameConflictBehavior) {
        this.fileNameConflictBehavior = fileNameConflictBehavior;
    }

    public String getUploadMethod() {
        return uploadMethod;
    }

    public void setUploadMethod(String uploadMethod) {
        this.uploadMethod = uploadMethod;
    }
}
