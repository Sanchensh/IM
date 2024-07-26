package com.mrxu.remote.service;

import com.mrxu.common.domain.biz.UserEvent;
import com.mrxu.remote.domain.RemotingCommand;

/**
 * @Description: 消息发送的服务
 * @author: ztowh
 * @Date: 2019-01-09 09:43
 */
public interface MessageSendService {

    void asyncSendMessage(String key, RemotingCommand command);

    /**
     * 发送离线消息
     *
     * @param key
     * @param command
     */
    void asyncSendOfflineMessage(String key, RemotingCommand command);

    void asyncSendGroupMsg(String key, RemotingCommand command);

    /**
     * 发送公众号消息
     *
     * @param key
     * @param command
     */
    void asyncSendPublicMsg(String key, RemotingCommand command);


    /**
     * 用户事件消息
     *
     * @param key
     * @param userEvent
     */
    void asyncSendUserEvent(String key, UserEvent userEvent);

    /**
     * 离线推送消息
     *
     * @param key
     * @param command
     */
    void asyncSendOfflinePushMessage(String key, RemotingCommand command);

    /**
     * 离线消息偏移量
     * @param key userId
     * @param command ImRequestCommand
     */
    void asyncSendOfflineMsgOffsetMessage(String key,RemotingCommand command);

}
