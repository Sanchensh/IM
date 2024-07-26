package com.mrxu.common.domain.biz.request;


import com.alibaba.fastjson.annotation.JSONField;
import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

import java.util.Map;

@Data
public class PushTxtCommand extends AbstractCommand {

    /**
     * 消息接受者
     */
    private String receiverId;

    /**
     * 消息发送者
     */
    private String senderId;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 应用Key
     */
    private String appKey;

    /**
     * 厂商平台(xiaomi, huawei, jpush ...)
     */
    private String pushBrand;

    /**
     * 第三方厂商推送registerID
     */
    private String pushRegId;

    /**
     * 额外配置
     */
    private Map<String, Object> extraParams;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.PUSH_MESSAGE;
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
}
