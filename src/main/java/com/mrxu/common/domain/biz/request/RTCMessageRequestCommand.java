package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

import java.util.Set;

@Data
public class RTCMessageRequestCommand extends AbstractCommand {

    private String senderId;

    private Set<String> receiverIds;

    private String userId;

    /**
     * 不允许离线推送(默认 false 即 允许离线推送)
     */
    private boolean disableOfflinePush;

    @Override
    public String getSenderId() {
        return senderId;
    }

    public RTCMessageRequestCommand setSenderId(String senderId) {
        this.senderId = senderId;
        return this;
    }

    public Set<String> getReceiverIds() {
        return receiverIds;
    }

    public RTCMessageRequestCommand setReceiverIds(Set<String> receiverIds) {
        this.receiverIds = receiverIds;
        return this;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    public RTCMessageRequestCommand setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public boolean isDisableOfflinePush() {
        return disableOfflinePush;
    }

    public RTCMessageRequestCommand setDisableOfflinePush(boolean disableOfflinePush) {
        this.disableOfflinePush = disableOfflinePush;
        return this;
    }

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.RTC_MESSAGE_REQ;
    }

}
