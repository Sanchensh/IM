package com.mrxu.common.domain.biz.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: zhaoyi.wang
 **/
@Data
@ToString
public class ImMessageVO implements Serializable {

    //    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long msgId;

    private Long parentMsgId;

    /**
     * offsetId
     */
    private Long offsetId;

    // 用于存放回复的消息ID
    private String conversationId;

    private String senderId;

    private String receiverId;

    private Boolean group;

    private String groupId;

    private Date sendTime;

    /**
     * 0: 文本消息
     * 1: 多媒体
     */
    private String msgType;

    /**
     * 聊天类型
     * 0: 私聊
     * 1: 群聊
     * 2: 公众号
     */
    private Byte chatType;

    private Byte status;

    private String content;

    private String docUrl;

    /**
     * 回复数量
     */
    private Integer replyCount;

    /**
     * 已阅数量
     */
    private Integer readCount;

    private Short cmdCode;

    private boolean modify;

    /**
     * 消息明细
     */
    private Map<String, List<MessageModifyDetailVO>> modifyDetail;

    /**
     * 扩展信息
     */
    private Map<String, Object> extraParams;

}
