package com.mrxu.common.domain.biz.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author: zhaoyi.wang
 * @date: 2019-02-18 10:51
 * @description:
 */
@Data
public class OfflineMessageVO implements Serializable {

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long msgId;

    private String senderId;

	private String recieverId;

    private Date sendTime;

    private String conversationId;

    /**
     * 0: 文本消息
     * 1: 图片
     * 2: 文件
     * 3: 视频
     * 25: 机器人消息
     * 3xx: 扩展消息
     */
    private Short msgType;

    private String content;

    private String docUrl;

    private Boolean group;

    private String groupId;

    /**
     * 扩展信息
     */
    private Map<String, Object> extraParams;

}
