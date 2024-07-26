package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

import java.util.List;

/**
 * @author: zhaoyi.wang
 * @date: 2019-02-14 09:30
 * @description: 未读消息已读回执
 */
@Data
public class OfflineMessageReadRequestCommand extends AbstractCommand {

    /**
     * 消息接收者
     */
    private String userId;

    /**
     * 消息发送者
     */
    private String senderId;

    /**
     * 消息ID集合
     */
    private List<String> msgIds;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.OFFLINE_MESSAGE_READ_REQ;
    }

    @Override
    public Boolean needCallback() {
        return false;
    }
}
