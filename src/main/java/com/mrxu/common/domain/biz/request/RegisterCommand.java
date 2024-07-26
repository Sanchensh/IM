package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

import java.util.List;

@Data
public class RegisterCommand extends AbstractCommand {

    private String userId;
    private String token;
    private String deviceId;
    //    private String appName;
    private String appKey;
    /**
     * android/ios
     */
    private String platform;
    private String screenSize;
    private String pkg;
    /**
     * 是否是重连
     * 0：否
     * 1：是
     */
    private byte reconnect;
    private List<String> tags;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.REGISTER_REQ;
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
