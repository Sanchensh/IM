package com.mrxu.common.domain.biz.response;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;
import lombok.ToString;

/**
 * @author: zhaoyi.wang
 **/
@Data
@ToString
public class MuteNotificationResponseCommand extends AbstractCommand {

    private String userId;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.MUTE_NOTIFICATION_RES;
    }

    @Override
    public String getSenderId() {
        return null;
    }
}
