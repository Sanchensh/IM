package com.mrxu.common.domain.biz.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

/**
 * @author: zhaoyi.wang
 **/
@Data
@ToString
public class PushInfo implements Serializable {

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息子标题
     */
    private String subTitle;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用Key
     */
    private String appKey;

    /**
     * 厂商平台(xiaomi, huawei, jpush ...)
     */
    private String pushBrand;

    /**
     * 第三方厂商推送registerID
     */
    private String pushRegId;

    /**
     * 角标
     */
    private Integer badge;

    /**
     * 提示音
     */
    private String sound;

    /**
     * 推送环境
     * 开发环境: dev
     * 生产环境: pro
     */
    private String env;

    /**
     * 推送额外参数
     */
    Map<String, Object> extraParams;

}
