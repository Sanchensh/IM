package com.mrxu.remote.service.impl;
import com.mrxu.common.domain.biz.UserEvent;
import com.mrxu.mq.ZMSTemplate;
import com.mrxu.remote.ZimConfiguration;
import com.mrxu.remote.domain.RemotingCommand;
import com.mrxu.remote.service.MessageSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2019-01-09 10:26
 */
@Component
public class MessageSendServiceImpl implements MessageSendService {

    @Autowired
    private ZMSTemplate zmsTemplate;
    @Autowired
    private ZimConfiguration zimConfiguration;

    @Override
    public void asyncSendMessage(String key, RemotingCommand command) {
        zmsTemplate.send(zimConfiguration.getMsgTopic(), command.getCmdCode().name(), key, command);
    }

    @Override
    public void asyncSendOfflineMessage(String key, RemotingCommand command) {
        zmsTemplate.send(zimConfiguration.getOfflineMsgTopic(), command.getCmdCode().name(), key, command);
    }

    @Override
    public void asyncSendGroupMsg(String key, RemotingCommand command) {
        zmsTemplate.send(zimConfiguration.getGroupMsgTopic(), command.getCmdCode().name(), key, command);
    }

    /**
     * 发送公众号消息
     *
     * @param key
     * @param command
     */
    @Override
    public void asyncSendPublicMsg(String key, RemotingCommand command) {
        zmsTemplate.send(zimConfiguration.getPublicMsgTopic(), command.getCmdCode().name(), key, command);
    }

    /**
     * 用户事件消息
     *
     * @param key
     * @param userEvent
     */
    @Override
    public void asyncSendUserEvent(String key, UserEvent userEvent) {
        zmsTemplate.send(zimConfiguration.getUserEventTopic(), userEvent.getEventType().getType(), key, userEvent);
    }

    /**
     * 离线推送消息
     *
     * @param key
     * @param command
     */
    @Override
    public void asyncSendOfflinePushMessage(String key, RemotingCommand command) {
        zmsTemplate.send(zimConfiguration.getOfflinePushMessage(), command.getCmdCode().name(), key, command);
    }

    @Override
    public void asyncSendOfflineMsgOffsetMessage(String key, RemotingCommand command) {
        zmsTemplate.send(zimConfiguration.getOfflineMsgOffsetTopic(), command.getCmdCode().name(), key, command);
    }

}
