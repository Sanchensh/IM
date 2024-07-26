package com.mrxu.remote.converter;

import com.mrxu.common.ImCommandCode;
import com.mrxu.event.common.EventType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zhaoyi.wang
 * @date: 2019-01-11 15:47
 * @description:
 */
@Slf4j
@Component
public class EventConverterManager {

    @Autowired
    private List<EventConverter> eventConverters;

    private Map<ImCommandCode, EventConverter> eventCommandConverterMap = new HashMap<>();
    private Map<EventType, EventConverter> eventTypeConverterMap = new HashMap<>();

    @PostConstruct
    public void registerCommandService() {
        if (CollectionUtils.isNotEmpty(eventConverters)) {
            eventConverters.forEach(eventConverter -> {
                eventCommandConverterMap.put(eventConverter.getImCommandCode(), eventConverter);
                eventTypeConverterMap.put(eventConverter.getEventType(), eventConverter);
            });
        }
    }

    public EventConverter getByCommandCode(ImCommandCode commandCode) {
        EventConverter eventConverter = eventCommandConverterMap.get(commandCode);
        if (eventConverter == null) {
            log.error("不支持的事件转换器, commandCode: {}", commandCode);
            throw new UnsupportedOperationException("不支持的事件转换器");
        }
        return eventConverter;
    }

    public EventConverter getByEventType(EventType eventType) {
        EventConverter eventConverter = eventTypeConverterMap.get(eventType);
        if (eventConverter == null) {
            log.error("不支持的事件转换器, eventType: {}", eventType);
            throw new UnsupportedOperationException("不支持的事件转换器");
        }
        return eventConverter;
    }

}
