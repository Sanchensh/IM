package com.mrxu.common.domain.biz.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2018/12/5 13:31
 */
@Data
public class TxtRequestCommand extends AbstractCommand {

    private String senderId;
    private String recieverId;
    private String content;
    private String docUrl;
    private String type;
    /**
     * 是否群组消息
     */
    private boolean group;
    private String groupId;
    private long parentMsgId;
    private boolean disableDeliver;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.TXT_REQ;
    }

    @Override
    @JSONField(serialize = false)
    public String getUserId() {
        return this.recieverId;
    }

}
