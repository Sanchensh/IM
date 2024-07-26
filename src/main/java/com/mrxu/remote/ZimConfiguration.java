package com.mrxu.remote;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2018-12-20 17:45
 */
@ConfigurationProperties(prefix = "zim")
@Component
@Data
public class ZimConfiguration {

    private int port;
    private String registry;
    private String msgTopic;
    private String eventTopic;
    private String msgAckTopic;
    private String eventAckTopic;
    private String offlineMsgTopic;
    private String zkPath;
    // 不注册到redis
    private boolean notRegister;
    // 群组topic
    private String groupMsgTopic;
    // 公众号topic
    private String publicMsgTopic;

    // 用户事件topic(上线/下线/添加/删除)
    private String userEventTopic;

    // 离线推送topic
    private String offlinePushMessage;

    /**
     * 离线消息偏移量记录topic
     */
    private String offlineMsgOffsetTopic;

    /**
     * tf_zim_group_robot_outgoing_msg
     */
    private String groupRobotOutgoingMessageTopic;

}
