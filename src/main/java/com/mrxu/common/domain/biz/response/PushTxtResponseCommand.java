package com.mrxu.common.domain.biz.response;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @author: zhaoyi.wang
 **/
@Data
public class PushTxtResponseCommand extends AbstractCommand {

    private String userId;

    private String senderId;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.PUSH_MESSAGE_RES;
    }

}
