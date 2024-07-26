package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

@Data
public class WebPushRequestCommand extends AbstractCommand {

    /**
     * 消息ID
     */
    private String msgId;

    /**
     * 消息发送者
     */
    private String senderId;

    /**
     * 消息接受者
     */
    private String receiverId;

    /**
     * 标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 角标
     */
    private Integer badge;

    /**
     * 提示音
     */
    private String sound;

    /**
     * 应用appKey
     */
    private String appKey;

    @Override
    public String getUserId() {
        return receiverId;
    }

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.WEB_PUSH_REQ;
    }

}
