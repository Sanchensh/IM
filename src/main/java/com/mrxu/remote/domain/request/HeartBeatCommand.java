package com.mrxu.remote.domain.request;

import com.mrxu.common.ImCommandType;
import com.mrxu.common.utils.SnowflakeIDGenUtils;

/**
 * @Description: 心跳包
 * @author: ztowh
 * @Date: 2018/11/22 16:01
 */
public class HeartBeatCommand extends ImRequestCommand {


    public HeartBeatCommand() {
        super(ImCommandType.HEARTBEAT);
        this.setId(SnowflakeIDGenUtils.getId());
    }
}
