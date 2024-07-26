package com.mrxu.common.domain.files.request;

/**
 * 请求头，第三方存储入参
 *
 * @author feng.chuang
 * @date 2021-04-25 13:52
 */
public class HeaderParameter {
    /**
     * Basic base64(AppID:AppSecret)
     */
    private String authorization;

    /**
     * Content-type
     */
    private String contentType;

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
