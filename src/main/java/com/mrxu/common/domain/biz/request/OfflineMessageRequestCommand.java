package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @author: zhaoyi.wang
 * @date: 2019-02-12 13:31
 * @description:
 */
@Data
public class OfflineMessageRequestCommand extends AbstractCommand {

    // 消息类型 0: 离线消息 1：历史消息
    private short type;

    private String userId;

    private String senderId;

    private Long msgId;

    /**
     * 每次获取消息数量, default: 100
     */
    private Integer msgSize;

    @Override
    public Boolean needCallback() {
        return false;
    }

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.OFFLINE_MESSAGE_REQ;
    }

}
