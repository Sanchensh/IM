package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @Description: 认证请求
 * @author: ztowh
 * @Date: 2018/11/28 16:27
 */
@Data
public class AuthRequestCommand extends AbstractCommand {

    private String userId;

    private String token;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.AUTH_REQ;
    }

    @Override
    public String getSenderId() {
        return null;
    }


}
