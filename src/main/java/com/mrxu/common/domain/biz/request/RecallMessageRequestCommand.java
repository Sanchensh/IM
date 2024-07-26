package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import com.mrxu.common.enums.ChatType;
import lombok.Data;

import java.util.Objects;

/**
 * @author: zhaoyi.wang
 * @description: 撤回消息命令
 **/
@Data
public class RecallMessageRequestCommand extends AbstractCommand {

    /**
     * 消息发送者
     */
    private String senderId;

    /**
     * 消息接收者
     */
    private String receiverId;

    private String groupId;

    /**
     * 需撤回的消息ID
     */
    private Long msgId;

    /**
     * 是否撤回群组消息
     */
    private boolean group;

    /**
     * 聊天类型
     * 0: 私聊
     * 1: 群聊
     * 2: 公众号
     */
    private Byte chatType;

    public boolean isGroup() {
        return group || Objects.equals(chatType, ChatType.GROUP);
    }

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.RECALL_MESSAGE_REQ;
    }

    @Override
    public String getUserId() {
        return this.receiverId;
    }


}
