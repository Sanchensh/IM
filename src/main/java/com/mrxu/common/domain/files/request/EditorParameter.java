package com.mrxu.common.domain.files.request;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 获取在线编辑地址参数，第三方存储入参
 *
 * @author feng.chuang
 * @date 2021-04-25 14:59
 */
public class EditorParameter {
    /**
     * 预览的过期时间(格式:2019-08-25T14:15:58+08:00)
     */
    private String expiration;

    /**
     * 水印内容
     */
    @JSONField(name = "watermark_text")
    private String watermarkText;

    /**
     *传入的外部用户Id(业务用户Id，用户唯一标识),必填
     */
    @JSONField(name = "ext_userid")
    private String extUserId;

    /**
     *传入的外部用户昵称
     */
    @JSONField(name = "ext_username")
    private String extUserName;


    /**
     * 是否用户同步(1：是，0：否),[默认:1]
     * 注意:同步用户传0，没做过传入1，传入1会即时同步出创建用户并模拟登陆，返回wps_id
     */
    @JSONField(name = "account_sync")
    private String accountSync;

    /**
     * 传入的外部公司Id
     */
    @JSONField(name = "ext_companyid")
    private String extCompanyId;
    /**
     * 历史版本权限(1：是，0：否)[默认:1]
     */
    private String history;

    /**
     * 写编辑权限 (1：是，0：否)[默认为1]
     */
    private String write;

    /**
     * 复制权限 (1：是，0：否)[默认为1]
     */
    private String copy;

    /**
     * 打印权限 (1：是，0：否)[默认为1]
     */
    private String print;

    /**
     * 导出权限 (1：是，0：否)[默认为0]
     */
    private String export;

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getWatermarkText() {
        return watermarkText;
    }

    public void setWatermarkText(String watermarkText) {
        this.watermarkText = watermarkText;
    }

    public String getExtUserId() {
        return extUserId;
    }

    public void setExtUserId(String extUserId) {
        this.extUserId = extUserId;
    }

    public String getExtUserName() {
        return extUserName;
    }

    public void setExtUserName(String extUserName) {
        this.extUserName = extUserName;
    }

    public String getAccountSync() {
        return accountSync;
    }

    public void setAccountSync(String accountSync) {
        this.accountSync = accountSync;
    }

    public String getExtCompanyId() {
        return extCompanyId;
    }

    public void setExtCompanyId(String extCompanyId) {
        this.extCompanyId = extCompanyId;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getWrite() {
        return write;
    }

    public void setWrite(String write) {
        this.write = write;
    }

    public String getCopy() {
        return copy;
    }

    public void setCopy(String copy) {
        this.copy = copy;
    }

    public String getPrint() {
        return print;
    }

    public void setPrint(String print) {
        this.print = print;
    }

    public String getExport() {
        return export;
    }

    public void setExport(String export) {
        this.export = export;
    }
}
