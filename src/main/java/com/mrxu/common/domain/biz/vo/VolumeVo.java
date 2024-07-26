package com.mrxu.common.domain.biz.vo;

import java.util.Map;

/**
 * volume vo
 *
 * @author feng.chuang
 * @date 2021-04-25 17:22
 */
public class VolumeVo {
    /**
     * 卷的唯一Id
     */
    private String id;

    /**
     * 卷名称
     */
    private String name;

    /**
     * 公司标识
     */
    private String companyId;

    /**
     * 创建时间
     */
    private String createAt;

    /**
     * 创建者标识
     */
    private Map<String,Object> createdBy;

    /**
     * 修改时间
     */
    private String updatedAt;

    /**
     * 修改者标识
     */
    private Map<String,Object> updatedBy;

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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
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
}
