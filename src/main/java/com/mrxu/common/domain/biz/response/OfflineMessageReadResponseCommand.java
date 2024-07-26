package com.mrxu.common.domain.biz.response;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @author: zhaoyi.wang
 * @date: 2019-02-14 09:33
 * @description: 未读消息已读回执ack
 */
@Data
public class OfflineMessageReadResponseCommand extends AbstractCommand {

    private String userId;

    private Boolean status;

    private String errorMsg;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.OFFLINE_MESSAGE_READ_RES;
    }

    @Override
    public String getSenderId() {
        return null;
    }
}
