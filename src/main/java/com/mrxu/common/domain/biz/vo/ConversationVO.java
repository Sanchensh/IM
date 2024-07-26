package com.mrxu.common.domain.biz.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author: zhaoyi.wang
 **/
@Data
@ToString
public class ConversationVO implements Serializable {

    /**
     * 消息id
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long msgId;

    /**
     * 发送者名称
     */
    private String senderName;

    /**
     * 父消息id
     */
    private Long parentMsgId;

    /**
     * 消息发送者id
     */
    private String senderId;

    private String receiverId;

    /**
     * 消息类型
     */
    private String msgType;

    /**
     * 消息内容
     */
    private String content;

    private String docUrl;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 未读数量
     */
    private Integer count;

    private boolean group;

    /**
     * 会话是否置顶
     */
    private Boolean top;

    /**
     * 会话置顶的时间
     */
    private Date topTime;

    /**
     * 聊天类型
     * 0: 单聊
     * 1: 群聊
     * 2: 公众号
     */
    private Byte chatType;

    /**
     * 消息状态（已读/状态）
     */
    private Integer status;

    /**
     * 标签信息（@消息）
     */
    private ConversationTagVO tagInfo;

    /**
     * 扩展信息
     */
    private Map<String, Object> extraParams;

}
