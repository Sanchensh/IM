package com.mrxu.common.domain.files.response;

import java.util.Date;

/**
 * 客户端统一文件操作响应
 *
 * @author feng.chuang
 * @date 2021-04-29 12:44
 */
public class FileResponse {
    /**
     * 资源唯一Id，文件和文件夹的唯一Id
     */
    private String resourceId;

    /**
     * 资源的类型，file-普通文件,folder-文件夹
     */
    private String type;

    /**
     * 资源的名称，文件夹名称，文件名称(带扩展名)
     */
    private String name;

    /**
     * 资源的父文件夹Id，根路径Id为conversation_id
     */
    private String parentId;

    /**
     * 资源的内容类型，文件为扩展名，文件夹为folder
     */
    private String contentType;

    /**
     * 资源的大小，文件大小
     */
    private Long size;

    /**
     * 文件md5值
     */
    private String md5;

    /**
     * 资源标签
     */
    private String tags;

    /**
     * 资源的状态，0-删除，1-正常
     */
    private Byte status;

    /**
     * 视频文件第一帧的图片地址
     */
    private String framePic;

    /**
     * 资源是否可编辑
     */
    private Boolean editable;

    /**
     * 资源是否可预览
     */
    private Boolean preview;

    /**
     * 创建者Id
     */
    private String createId;

    /**
     * 更新者Id
     */
    private String updateId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public Boolean getPreview() {
        return preview;
    }

    public void setPreview(Boolean preview) {
        this.preview = preview;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getFramePic() {
        return framePic;
    }

    public void setFramePic(String framePic) {
        this.framePic = framePic;
    }
}
