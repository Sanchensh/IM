package com.mrxu.common.domain.files.request;

/**
 * 预览参数，第三方存储入参
 *
 * @author feng.chuang
 * @date 2021-04-25 14:44
 */
public class PreviewParameter {
    /**
     * 过期时间
     */
    private String expiration;

    /**
     * 内容可复制
     */
    private Boolean copyable;

    /**
     * 内容可打印
     */
    private Boolean printable;

    /**
     * 水印文本
     */
    private String watermarkText;

    /**
     * 水印图片
     */
    private String watermarkImageUrl;

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public Boolean getCopyable() {
        return copyable;
    }

    public void setCopyable(Boolean copyable) {
        this.copyable = copyable;
    }

    public Boolean getPrintable() {
        return printable;
    }

    public void setPrintable(Boolean printable) {
        this.printable = printable;
    }

    public String getWatermarkText() {
        return watermarkText;
    }

    public void setWatermarkText(String watermarkText) {
        this.watermarkText = watermarkText;
    }

    public String getWatermarkImageUrl() {
        return watermarkImageUrl;
    }

    public void setWatermarkImageUrl(String watermarkImageUrl) {
        this.watermarkImageUrl = watermarkImageUrl;
    }
}
