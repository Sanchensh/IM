package com.mrxu.common.domain.biz.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author: zhaoyi.wang
 **/
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class ConversationInfoVO extends ConversationVO implements Serializable {
    /**
     * 会话名称
     */
    private String conversationName;

    /**
     * 禁止回复
     */
    private boolean disableReply;

    /**
     * 是否免打扰
     */
    private boolean mute;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 工作状态
     */
    private String workStatus;

    /**
     * 群类型（内部群，部门群，外部群，服务群。。。）
     */
    private Integer teamType;

    /**
     * 会话跳转地址
     */
    private String linkUrl;

}
