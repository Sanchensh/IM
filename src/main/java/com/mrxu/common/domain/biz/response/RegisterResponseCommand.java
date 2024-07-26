package com.mrxu.common.domain.biz.response;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @author: zhaoyi.wang
 **/
@Data
public class RegisterResponseCommand extends AbstractCommand {

    private boolean status;

    private String message;

    private String userId;

    private String registrationId;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.REGISTER_RES;
    }

    @Override
    public String getSenderId() {
        return null;
    }

}
