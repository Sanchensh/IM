package com.mrxu.common.domain.files.response;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 获取token时的响应信息
 *
 * @author feng.chuang
 * @date 2021-04-25 15:35
 */
public class TokenResponse {
    /**
     * accessToken
     */
    @JSONField(name="access_token")
    private String accessToken;

    /**
     * access_token有效期，以秒为单位
     */
    @JSONField(name="expires_in")
    private Long expiresIn;

    /**
     * access_token最终访问的范围，即实际授予的权限列表
     */
    @JSONField(name="scope")
    private String scope;

    /**
     * token类型
     */
    @JSONField(name="token_type")
    private String tokenType;

    /**
     * 用于刷新access_token的refresh_token
     */
    @JSONField(name="refresh_token")
    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
