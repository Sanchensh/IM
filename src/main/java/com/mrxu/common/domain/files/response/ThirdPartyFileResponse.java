package com.mrxu.common.domain.files.response;

import java.util.Map;

/**
 * 创建文件，包括文件和文件夹的基础响应属性，第三方响应
 *
 * @author feng.chuang
 * @date 2021-04-26 10:45
 */
public class ThirdPartyFileResponse {
    /**
     * 文件唯一标识，文件夹和文件都有一个唯一标识
     */
    private String id;

    /**
     * 文件或文件夹名称
     */
    private String name;

    /**
     * 文件类型,枚举值
     * regular - 常规文件
     * folder - 文件夹
     */
    private String type;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 卷Id
     */
    private String volumeId;

    /**
     * 路径(目前搜索接口适用)
     */
    private String path;

    /**
     * 父文件夹标识,唯一ID,根路径为root
     */
    private String parentId;

    /**
     * 文件内容hashes,选一个就行,枚举值
     * sha1 - 文件sha1值
     * md5 - 文件md5值
     */
    private Map<String, Object> hashes;
    /**
     * 创建时间
     */
    private String createdAt;

    /**
     * 创建者标识
     */
    private Map<String, Object> createdBy;

    /**
     * 修改时间
     */
    private String updatedAt;

    /**
     * 修改者标识
     */
    private Map<String, Object> updatedBy;

    /**
     * (文件权限列表(用户身份才有该字段),枚举值
     * read new_empty upload update rename move copy share delete download secret
     */
    private String[] permissions;

    private String framePicUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(String volumeId) {
        this.volumeId = volumeId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Map<String, Object> getHashes() {
        return hashes;
    }

    public void setHashes(Map<String, Object> hashes) {
        this.hashes = hashes;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Map<String, Object> getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Map<String, Object> createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Map<String, Object> getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Map<String, Object> updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }

    public String getFramePicUrl() {
        return framePicUrl;
    }

    public void setFramePicUrl(String framePicUrl) {
        this.framePicUrl = framePicUrl;
    }
}
