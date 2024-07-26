package com.mrxu.common.domain.biz.response;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @author: zhaoyi.wang
 * @date: 2019-03-13 10:01
 * @description: 媒体消息ack
 */
@Data
public class DocResponseCommand extends AbstractCommand {

    private String userId;
    private long msgId;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.DOC_RES;
    }

    @Override
    public String getSenderId() {
        return null;
    }

}