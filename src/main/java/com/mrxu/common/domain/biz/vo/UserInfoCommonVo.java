package com.mrxu.common.domain.biz.vo;

/**
 * 为了UserEvent可以访问到，新建一个Vo类
 *
 * @author feng.chuang
 * @date 2021-06-19 15:16
 */
public class UserInfoCommonVo {
    private String biz;
    private String userId;
    private String realName;

    public String getBiz() {
        return biz;
    }

    public void setBiz(String biz) {
        this.biz = biz;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
