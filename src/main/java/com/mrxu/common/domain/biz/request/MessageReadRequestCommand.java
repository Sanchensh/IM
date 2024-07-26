package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @author: zhaoyi.wang
 **/
@Data
public class MessageReadRequestCommand extends AbstractCommand {

    private String senderId;

    private String receiverId;

    private String groupId;

    /**
     * 最大消息ID
     */
    private Long msgId;

    /**
     * 最大的群消息Id(消息父id)
     */
    private Long parentMsgId;

    /**
     * 已读消息数量
     */
    private Long count;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.MESSAGE_READ_REQ;
    }

    @Override
    public String getUserId() {
        return this.receiverId;
    }
}
