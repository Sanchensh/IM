package com.mrxu.common.domain.biz.response;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2018-12-21 11:10
 */
@Data
public class CommonResponseCommand extends AbstractCommand {

    private boolean status;
    private long msgId;
    private long arriveTime;
    private String errorMsg;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.TXT_RES;
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
