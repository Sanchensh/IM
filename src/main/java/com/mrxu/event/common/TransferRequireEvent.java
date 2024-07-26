package com.mrxu.event.common;

import lombok.Data;

/**
 * @Description: 转人工请求
 * @author: ztowh
 * @Date: 2018-12-25 13:32
 */
@Data
public class TransferRequireEvent extends Event {

    @Override
    public EventType getEventType() {
        return EventType.TRANSFER_REQUIRE_EVENT;
    }
}
