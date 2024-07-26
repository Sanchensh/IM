package com.mrxu.common.domain.files.request;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 创建文件夹参数
 *
 * @author feng.chuang
 * @date 2021-04-26 10:15
 */
public class CreateDirParameter {
    /**
     * 要创建的文件夹名称
     */
    @JSONField(name = "file_name")
    private String fileName;
    /**
     * 文件类型
     * folder - 文件夹
     */
    @JSONField(name = "file_type")
    private String fileType;

    /**
     * 文件名冲突行为，枚举值
     * fail - 失败
     * rename - 重命名
     */
    @JSONField(name = "file_name_conflict_behavior")
    private String fileNameConflictBehavior;

    /**
     * 如果父目录不存在，是否创建之。默认创建
     */
    @JSONField(name = "@parents")
    private Boolean parent;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileNameConflictBehavior() {
        return fileNameConflictBehavior;
    }

    public void setFileNameConflictBehavior(String fileNameConflictBehavior) {
        this.fileNameConflictBehavior = fileNameConflictBehavior;
    }

    public Boolean getParent() {
        return parent;
    }

    public void setParent(Boolean parent) {
        this.parent = parent;
    }
}
