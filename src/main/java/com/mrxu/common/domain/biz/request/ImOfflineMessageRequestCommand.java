package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;
import lombok.ToString;

/**
 * @author: zhaoyi.wang
 **/
@Data
@ToString
public class ImOfflineMessageRequestCommand extends AbstractCommand {

    private String senderId;
    private String receiverId;
    private String groupId;
    private Long msgId;
    private Integer msgSize;
    /**
     * 拉取消息类型
     * 1: 离线消息
     * 2: 历史消息(before)
     * 3: 历史消息(after)
     * 4: 回复消息
     * 5: middle
     */
    private Short type;

    private boolean includeModify;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.IM_OFFLINE_MSG_REQ;
    }

    @Override
    public String getUserId() {
        return this.receiverId;
    }

    @Override
    public Boolean needCallback() {
        return false;
    }

}
