package com.mrxu.common.domain.biz.response;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @author: zhaoyi.wang
 **/
@Data
public class MessageModifyResponseCommand extends AbstractCommand {

    private boolean status;
    private long msgId;
    private long receiveTime;
    private String errorMsg;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.MESSAGE_MODIFY_RES;
    }

    @Override
    public String getUserId() {
        return null;
    }

    @Override
    public String getSenderId() {
        return null;
    }

}
