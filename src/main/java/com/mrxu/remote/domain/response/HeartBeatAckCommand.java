package com.mrxu.remote.domain.response;

import com.mrxu.common.ResponseStatus;

/**
 * @Description: 心跳响应
 * @author: ztowh
 * @Date: 2018/11/22 16:15
 */
public class HeartBeatAckCommand extends ImResponseCommand {

    public HeartBeatAckCommand() {
//		super(ImCommandType.HEARTBEATACK);
        this.setResponseStatus(ResponseStatus.SUCCESS);
    }
}
