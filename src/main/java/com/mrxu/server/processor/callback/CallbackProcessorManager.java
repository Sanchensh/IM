package com.mrxu.server.processor.callback;

import com.mrxu.common.ImCommandCode;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zhaoyi.wang
 * @date: 2019-01-10 14:41
 * @description:
 */
@Component
public class CallbackProcessorManager {

    @Autowired(required = false)
    private List<CallbackProcessor> callbackProcessors;

    private final Map<ImCommandCode, CallbackProcessor<?>> processors = new HashMap<>();

    @PostConstruct
    public void registerCallbackProcessor() {
        if (CollectionUtils.isNotEmpty(callbackProcessors)) {
            callbackProcessors.forEach(processor -> {
                processors.put(processor.getCmdCode(), processor);
            });
        }
    }

    public CallbackProcessor getCallbackProcessor(ImCommandCode commandCode) {
        return processors.get(commandCode);
    }

}
