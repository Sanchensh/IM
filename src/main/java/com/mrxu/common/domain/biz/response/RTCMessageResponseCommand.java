package com.mrxu.common.domain.biz.response;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;

/**
 * @author: zhaoyi.wang
 * @date: 2020/6/19 10:34 上午
 * @descriptin:
 **/
public class RTCMessageResponseCommand extends AbstractCommand {

    private Boolean status;
    private String errorMsg;

    public Boolean getStatus() {
        return status;
    }

    public RTCMessageResponseCommand setStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public RTCMessageResponseCommand setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.RTC_MESSAGE_RES;
    }

    @Override
    public String getUserId() {
        return null;
    }

    @Override
    public String getSenderId() {
        return null;
    }

    @Override
    public String toString() {
        return "RTCMessageResponseCommand{" +
                "status=" + status +
                ", errorMsg='" + errorMsg + '\'' +
                "} " + super.toString();
    }
}
