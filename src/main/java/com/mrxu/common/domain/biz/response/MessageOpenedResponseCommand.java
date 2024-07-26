package com.mrxu.common.domain.biz.response;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;
import lombok.ToString;

/**
 * @author: zhaoyi.wang
 **/
@Data
@ToString
public class MessageOpenedResponseCommand extends AbstractCommand {

    private String userId;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.MESSAGE_OPENED_RES;
    }

    @Override
    public String getSenderId() {
        return null;
    }

}
