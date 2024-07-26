package com.mrxu.common.domain.biz.response;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2018/11/29 10:08
 */
@Data
public class AuthResponseCommand extends AbstractCommand {

    private boolean status;

    private String reason;

    private String userId;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.AUTH_RES;
    }

    @Override
    public String getSenderId() {
        return null;
    }

}