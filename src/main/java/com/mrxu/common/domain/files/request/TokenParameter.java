package com.mrxu.common.domain.files.request;

/**
 * token param，第三方存储入参
 *
 * @author feng.chuang
 * @date 2021-04-25 14:36
 */
public class TokenParameter {
    /**
     * 授权类型
     */
    private String grantType;

    /**
     * 授权码
     */
    private String code;

    /**
     * 授权后要回调的URI，必须与开发者注册应用时所提供的回调地址相匹配
     */
    private String redirectUri;


    /**
     * 以空格分隔的权限列表
     */
    private String scope;


    /**
     * refresh_token
     */
    private String refreshToken;

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
