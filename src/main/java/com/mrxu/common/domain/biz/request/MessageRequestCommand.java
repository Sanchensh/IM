package com.mrxu.common.domain.biz.request;



import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.annotation.JSONField;
import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;

import com.mrxu.common.domain.biz.vo.PushInfo;
import com.mrxu.common.enums.ChatType;
import lombok.Data;
import org.apache.commons.collections.MapUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Data
public class MessageRequestCommand extends AbstractCommand {

    /**
     * 消息发送者
     */
    private String senderId;

    /**
     * 消息接受者
     */
    private String receiverId;

    /**
     * 消息类型
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
     * 是否群组消息
     */
    private boolean group;

    /**
     * 群id
     */
    private String groupId;

    /**
     * 群消息id
     */
    private Long parentMsgId;

    /**
     * 不允许离线推送(默认 false 即 允许离线推送)
     */
    private boolean disableOfflinePush;

    /**
     * 额外配置
     */
    private Map<String, Object> extraParams;

    public boolean isGroup() {
        return group || Objects.equals(chatType, ChatType.GROUP);
    }

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.MESSAGE_REQ;
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
        if (MapUtils.isNotEmpty(extraParams) && extraParams.get("pushInfo") != null) {
            pushInfo = JSON.parseObject(String.valueOf(extraParams.get("pushInfo")), PushInfo.class);
        }
        return pushInfo;
    }

    public void setPushInfo(PushInfo pushInfo) {
        Map<String, Object> stringObjectMap = Optional.ofNullable(this.extraParams).orElse(Collections.emptyMap());
        if (pushInfo != null) {
            stringObjectMap.put("pushInfo", JSON.toJSON(pushInfo));
        }

    }
}
