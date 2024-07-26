package com.mrxu.common.domain.files.request;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 文件复制属性
 *
 * @author feng.chuang
 * @date 2021-04-25 15:21
 */
public class CopyParameter {
    /**
     * 要复制的文件名
     */
    @JSONField(name = "file_name")
    private String fileName;

    /**
     * 要复制的文件所在的文件夹id
     */
    @JSONField(name = "file_parent_id")
    private String fileParentId;

    /**
     * 要复制的文件所在的卷id
     */
    @JSONField(name = "file_volume_id")
    private String fileVolumeId;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileParentId() {
        return fileParentId;
    }

    public void setFileParentId(String fileParentId) {
        this.fileParentId = fileParentId;
    }

    public String getFileVolumeId() {
        return fileVolumeId;
    }

    public void setFileVolumeId(String fileVolumeId) {
        this.fileVolumeId = fileVolumeId;
    }
}
