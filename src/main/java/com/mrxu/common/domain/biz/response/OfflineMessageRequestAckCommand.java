package com.mrxu.common.domain.biz.response;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @author: zhaoyi.wang
 * @date: 2019-03-13 10:02
 * @description: 获取未读消息请求ack
 */
@Data
public class OfflineMessageRequestAckCommand extends AbstractCommand {

    private String userId;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.OFFLINE_MESSAGE_ACK;
    }

    @Override
    public String getSenderId() {
        return null;
    }

}