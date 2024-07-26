package com.mrxu.remote.converter;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.request.EvaluationResultCommand;
import com.mrxu.common.utils.CopyUtil;
import com.mrxu.event.common.EvaluationResultEvent;
import com.mrxu.event.common.EventType;
import com.mrxu.remote.domain.RemotingCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: zhaoyi.wang
 * @date: 2019-02-19 09:21
 * @description:
 */
@Slf4j
@Component
public class EvaluationResultEventConverter implements EventConverter<EvaluationResultEvent, EvaluationResultCommand> {

    @Override
    public EvaluationResultEvent convert2Event(byte channel, EvaluationResultCommand cmd) {
        if (cmd == null) {
            return null;
        }
        EvaluationResultEvent event = CopyUtil.copy(cmd, EvaluationResultEvent.class);
        event.setChannel(channel);
        return event;
    }

    @Override
    public EvaluationResultCommand convert2EventCommand(EvaluationResultEvent event) {
        return null;
    }

    @Override
    public RemotingCommand convert2remotingCommand(EvaluationResultEvent event) {
        return null;
    }

    @Override
    public EventType getEventType() {
        return null;
    }

    @Override
    public ImCommandCode getImCommandCode() {
        return null;
    }
}
