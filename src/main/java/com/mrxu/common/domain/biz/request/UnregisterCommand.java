package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @author: zhaoyi.wang
 **/
@Data
public class UnregisterCommand extends AbstractCommand {

    private String userId;

    private String registrationId;

    private String appKey;

    /**
     * 设备平台
     * android/ios
     */
    private String platform;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.UNREGISTER_REQ;
    }

    @Override
    public String getSenderId() {
        return null;
    }
}
