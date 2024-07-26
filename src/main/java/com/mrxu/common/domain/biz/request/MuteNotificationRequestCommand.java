package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;
import lombok.ToString;

/**
 * @author: zhaoyi.wang
 **/
@Data
@ToString
public class MuteNotificationRequestCommand extends AbstractCommand {

    private String userId;

    /**
     * 免打扰对象
     */
    private String muteUserId;

    /**
     * 类型(1: 用户, 2: 群组)
     */
    private Integer type;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.MUTE_NOTIFICATION_REQ;
    }

    @Override
    public String getSenderId() {
        return null;
    }
}
