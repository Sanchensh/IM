package com.mrxu.common.domain.files.response;

/**
 * 文件通用响应信息
 *
 * @author feng.chuang
 * @date 2021-05-06 10:10
 */
public class FileCommonResponse {
    /**
     * 下载，预览，在线编辑的实际地址
     */
    private String url;

    /**
     * 链接有效期
     */
    private String expiration;

    /**
     * 文件md5值
     */
    private String md5;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
