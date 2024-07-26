package com.mrxu.common;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2018-12-20 10:54
 */
public interface Constant {

    String colon = ":";

    String TRANSFER_TO_MAN = "转人工";

    String ROBOT = "robot";

    String DEFAULT_RECEIVER = "default";

    String TRANSFER_TO_KEFU = "TransferToKf";

    // 渠道
//    byte CHANNEL_SYSTEM = 0;    // 默认系统
//    byte CHANNEL_OFFICIAL = 1;  // 官网
//    byte CHANNEL_ROBOT = 2;     // 机器人
//    byte CHANNEL_CUSTOMER_SERVICES = 3; // 客服
//    byte CHANNEL_WECHAT = 4;    // 微信
//    byte CHANNEL_ALIPAY = 5;    // 支付宝
//    byte CHANNEL_PDD = 6;       // 拼多多

    // session超时时间，默认5分钟
    int SESSION_TIMEOUT = 60 * 60 * 24;

    // 客服系统的分表的reciever key统一使用这个
    String KEFU_RECIEVE_KEY = "kefu";

    // 指定通知的组的人
    String SPECIFIC_PERSON = "specificPerson";

    // 会话列表前缀
    String CONVERSATION_LIST_PREFIX = "conversation_list_";
    // 会话列表里的明细最新一条
    String CONVERSATION_PREFIX = "conversation_";

    Long EXECUTIVE_EXPIRE = 12 * 60 * 60 * 1000L;

    String EXECUTIVE_CHAT_PREFIX = "executive_chat:";

    String EXECUTIVE_PHONE_CALL_PREFIX = "executive_phone_call:";

    String GROUP_ROBOT_PREFIX = "robot";

}
