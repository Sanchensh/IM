package com.mrxu.common.domain.biz.request;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.annotation.JSONField;
import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import com.mrxu.common.domain.biz.vo.PushInfo;
import com.mrxu.common.enums.ChatType;
import lombok.Data;
import org.apache.commons.collections.MapUtils;

import java.util.Objects;

@Data
public class MessageModifyRequestCommand extends AbstractCommand {

    /**
     * 消息发送者
     */
    private String senderId;

    /**
     * 消息接受者
     */
    private String receiverId;

    /**
     * 消息类型, eg:
     * 0: 文本
     * 1: 图片
     * 2: 文件
     * 3: 视频
     * 4: 表情
     * 5: 自定义
     */
    private String type;

    /**
     * 聊天类型
     * 0: 私聊
     * 1: 群聊
     * 2: 公众号
     */
    private Byte chatType;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 多媒体链接
     */
    private String docUrl;

    /**
     * 群id
     */
    private String groupId;

    /**
     * 群消息id
     */
    private Long parentMsgId;

    /**
     * 消息推送级别
     * 0: 不推送
     * 1: 离线推送
     * 2: 免打扰离线推送
     * 3: 设备免打扰离线推送
     */
    private Byte pushLevel;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.MESSAGE_MODIFY_REQ;
    }

    public boolean isGroup() {
        return Objects.equals(chatType, ChatType.GROUP);
    }

    @Override
    public Boolean needCallback() {
        return true;
    }

    @Override
    @JSONField(serialize = false)
    public String getUserId() {
        return this.receiverId;
    }

    @JSONField(serialize = false)
    public PushInfo getPushInfo() {
        PushInfo pushInfo = null;
        if (MapUtils.isNotEmpty(getExtraParams()) && getExtraParams().get("pushInfo") != null) {
            pushInfo = JSON.parseObject(String.valueOf(getExtraParams().get("pushInfo")), PushInfo.class);
        }
        return pushInfo;
    }

}
