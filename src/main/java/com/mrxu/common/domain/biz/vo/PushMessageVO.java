package com.mrxu.common.domain.biz.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author: zhaoyi.wang
 **/
@Data
public class PushMessageVO implements Serializable {

    /**
     * 消息ID
     */
    private String msgId;

    /**
     * 消息接受者
     */
    private String receiverId;

    /**
     * 消息发送者
     */
    private String senderId;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 额外配置
     */
    private Map<String, Object> extraParams;

}
