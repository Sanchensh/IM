package com.mrxu.common.domain.biz.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import com.mrxu.common.enums.ChatType;
import lombok.Data;

import java.util.Map;
import java.util.Objects;

/**
 * @author: zhaoyi.wang
 * @date: 2019/12/4 8:52 上午
 * @description:
 */
@Data
public class NotificationRequestCommand extends AbstractCommand {

    /**
     * 消息发送者
     */
    private String senderId;

    /**
     * 消息接受者
     */
    private String receiverId;

    /**
     * 群组ID
     */
    private String groupId;

    /**
     * 是否群组消息
     */
    private boolean group;

    /**
     * 聊天类型
     * 0: 私聊
     * 1: 群聊
     * 2: 公众号
     */
    private Byte chatType;

    /**
     * 不允许离线推送(默认 false 即 允许离线推送)
     */
    private boolean disableOfflinePush;

    /**
     * 额外配置
     */
    private Map<String, Object> extraParams;

    public boolean isGroup() {
        return group || Objects.equals(chatType, ChatType.GROUP);
    }

    @Override
    @JSONField(serialize = false)
    public String getUserId() {
        return this.receiverId;
    }

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.NOTIFICATION_REQ;
    }

}
