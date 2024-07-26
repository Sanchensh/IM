package com.mrxu.common.domain.biz.response;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

import java.util.List;

@Data
public class MessageReaderListResponseCommand extends AbstractCommand {

    private Integer index;

    private List<String> readers;

    private Boolean status;

    private String errorMsg;

    @Override
    public String getUserId() {
        return null;
    }

    @Override
    public String getSenderId() {
        return null;
    }

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.MESSAGE_READER_LIST_RES;
    }

}
