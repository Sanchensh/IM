package com.mrxu.publisher;

import com.alibaba.fastjson.JSON;
import com.mrxu.event.common.Event;
import com.mrxu.event.interfaces.service.EventPublisherService;
import com.mrxu.mq.ZMSTemplate;
import com.mrxu.remote.ZimConfiguration;
import com.mrxu.remote.domain.RemotingCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: zhaoyi.wang
 * @date: 2019-01-09 11:08
 * @description:
 */
@Slf4j
@Component
public class EventPublisherServiceImpl implements EventPublisherService {

    @Autowired
    private ZMSTemplate zmsTemplate;

    @Autowired
    private ZimConfiguration zimConfiguration;

    @Override
    public void publish(Event event) {
        try {
            log.info("发布事件: {}", JSON.toJSON(event));
            zmsTemplate.send(zimConfiguration.getEventTopic(), event.getEventType().getType(), event.getUserId(), (RemotingCommand) event);
        } catch (Exception e) {
            log.error("发送事件消息失败，event: {}", JSON.toJSON(event), e);
            throw e;
        }
    }

}
