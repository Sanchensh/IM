package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

import java.util.Date;

/**
 * @Description: 连接建立事件
 * @author: ztowh
 * @Date: 2018-12-25 13:07
 */
@Data
public class ConnectionEstablishCommand extends AbstractCommand {

    private String userId;

    private Date eventTime;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.CONNECT_EVENT;
    }

    @Override
    public Boolean needCallback() {
        return false;
    }

    @Override
    public String getSenderId() {
        return null;
    }

}
