package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @author: zhaoyi.wang
 * @data 2020/11/30 9:05 上午
 * @description:
 */
@Data
public class DeleteMessageRequestCommand extends AbstractCommand {

    private String senderId;

    private String receiverId;

    private String groupId;

    /**
     * 最大消息ID
     */
    private Long msgId;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.DELETE_MESSAGE_REQ;
    }

    @Override
    public String getUserId() {
        return this.receiverId;
    }

    @Override
    public Boolean needCallback() {
        return false;
    }
}
