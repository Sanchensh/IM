package com.mrxu.common.domain.files.response;

import java.util.Map;

/**
 * 文件下载信息响应
 *
 * @author feng.chuang
 * @date 2021-05-01 11:12
 */
public class DownloadResponse {
    /**
     * 用于文件下载的真实url
     */
    private String url;

    /**
     * 内部下载地址,用于容器内的下载链接
     */
    private String innerUrl;

    /**
     * 过期时间
     */
    private String expiration;

    /**
     * 文件hash值
     */
    private Map<String,Object> hashes;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInnerUrl() {
        return innerUrl;
    }

    public void setInnerUrl(String innerUrl) {
        this.innerUrl = innerUrl;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public Map<String, Object> getHashes() {
        return hashes;
    }

    public void setHashes(Map<String, Object> hashes) {
        this.hashes = hashes;
    }
}
