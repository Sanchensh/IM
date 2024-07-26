package com.mrxu.common.enums;

import java.util.Objects;

/**
 * @author: zhaoyi.wang
 * @description:
 **/
public enum CallbackChannel {

    /**
     * web渠道
     */
    WEB("web"),

    /**
     * 微信
     */
    WECHAT("wechat"),

    /**
     * 支付宝
     */
    CHANNEL_ALIPAY("alipay"),

    /**
     * 拼多多
     */
//    CHANNEL_PDD("pinduoduo"),

    /**
     * 菜鸟
     */
//    CHANNEL_CAINIAO("cainiao"),

    /**
     * 贝贝
     */
//    CHANNEL_BEIBEI("beibei"),
    ;

    private String channel;

    CallbackChannel(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }

    public static boolean hasChannel(String channel) {
        for (CallbackChannel cha : CallbackChannel.values()) {
            if (Objects.equals(channel, cha.channel)) {
                return true;
            }
        }
        return false;
    }
}
