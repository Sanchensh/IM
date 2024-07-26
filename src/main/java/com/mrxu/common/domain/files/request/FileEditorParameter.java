package com.mrxu.common.domain.files.request;

/**
 * 文件在线编辑请求参数
 *
 * @author feng.chuang
 * @date 2021-05-05 21:13
 */
public class FileEditorParameter {
    /**
     * 预览的过期时间(格式:2019-08-25T14:15:58+08:00)
     */
    private String expiration;

    /**
     * 水印内容
     */
    private String watermarkText;

    /**
     * 传入的外部用户Id(业务用户Id，用户唯一标识)
     */
    private String extUserId;

    /**
     * 传入的外部用户昵称
     */
    private String extUsername;

    /**
     * 是否需要进行用户同步(1：是，0：否)[默认:0],注意:已经做了账号同步的话传0，没做过传入1。传入1会立即同步，创建用户并模拟登陆，返回wps_sid
     */
    private String accountSync;

    /**
     * 传入的外部公司名称
     */
    private String extCompanyId;

    /**
     * 历史版本权限(1：是，0：否)[默认:0]
     */
    private String history;

    /**
     * 写编辑权限 (1：是，0：否)[默认为0]
     */
    private String write;

    /**
     * 打印权限 (1：是，0：否)[默认为1]
     */
    private String print;

    /**
     * 复制权限 (1：是，0：否)[默认为1]
     */
    private String copy;

    /**
     * 导出权限 (1：是，0：否)[默认为0]
     */
    private String export;

    /**
     * 重命名权限 (1：是，0：否)[默认为0]
     */
    private String rename;

    /**
     * 水印信息(如果存在，则会覆盖watermark_text的设置)
     */
    private WaterMark waterMark;

}
