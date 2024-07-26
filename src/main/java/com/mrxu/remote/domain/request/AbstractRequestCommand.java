package com.mrxu.remote.domain.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.ImCommandType;
import com.mrxu.remote.domain.ImCommand;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2018/11/26 10:26
 */
public abstract class AbstractRequestCommand extends ImCommand {

    private long arriveTime = -1;


    public AbstractRequestCommand() {
        super(ImCommandType.REQUEST);
    }

    public AbstractRequestCommand(byte type) {
        super(type);
    }

    public AbstractRequestCommand(ImCommandCode cmdCode) {
        super(ImCommandType.REQUEST, cmdCode);
    }

    public AbstractRequestCommand(byte channel, byte type, ImCommandCode cmdCode) {
        super(channel, type, cmdCode);
    }

    public long getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(long arriveTime) {
        this.arriveTime = arriveTime;
    }
}
