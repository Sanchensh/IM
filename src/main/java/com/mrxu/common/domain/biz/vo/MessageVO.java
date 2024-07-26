package com.mrxu.common.domain.biz.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author: zhaoyi.wang
 **/
@Data
public class MessageVO implements Serializable {

    /**
     * 消息ID
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long msgId;

    /**
     * 消息发送者
     */
    private String senderId;

    /**
     * 消息接受者
     */
    private String receiverId;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 0: 文本消息
     * 1: 图片
     * 2: 文件
     * 3: 视频
     */
    private String type;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息子标题
     */
    private String subTitle;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 媒体链接
     */
    private String docUrl;

    /**
     * 消息状态
     */
    private Integer status;

    /**
     * 额外配置
     */
    private Map<String, Object> extraParams;

}
