package com.mrxu.common.enums;

public enum Channel {

    // 默认系统
    SYSTEM((byte) 0),
    // 官网
    OFFICIAL((byte) 1),
    // 机器人
    ROBOT((byte) 2),
    // 客服
    CUSTOMER_SERVICES((byte) 3),
    // 微信
    WECHAT((byte) 4),
    // 支付宝
    ALIPAY((byte) 5),
    // 拼多多
    PDD((byte) 6),
    // 贝贝网
    BEIBEI((byte) 7),
    // 菜鸟
    CAINIAO((byte) 8),
    // 微信小程序
    WECHAT_MINI((byte) 9),
    // 快运菜鸟
    KUAIYUN_CAINIAO((byte) 8),

    // 微信第三方
    WECHAT_THIRDPARTY((byte) 101),

    // 吉信
    JIXIN_WEB((byte) 123),
    JIXIN_PC((byte) 124),
    JIXIN_IOS((byte) 125),
    JIXIN_ANDROID((byte) 126),
    // 推送
    PUSH((byte) 127),

    ;

    private byte channel;

    Channel(byte channel) {
        this.channel = channel;
    }

    public byte getChannel() {
        return channel;
    }

}