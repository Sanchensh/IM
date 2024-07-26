package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

@Data
public class MessageReaderListRequestCommand extends AbstractCommand {

    private String userId;

    private Integer index;

    private Long msgId;

    /**
     * 每页数量(最大200)
     */
    private Integer pageSize;


    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.MESSAGE_READER_LIST_REQ;
    }

    @Override
    public Boolean needCallback() {
        return false;
    }

    @Override
    public String getSenderId() {
        return this.userId;
    }
}
