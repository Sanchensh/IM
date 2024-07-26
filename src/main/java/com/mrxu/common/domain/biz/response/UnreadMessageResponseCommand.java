package com.mrxu.common.domain.biz.response;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import com.mrxu.common.domain.biz.vo.PushMessageVO;
import lombok.Data;

import java.util.List;

/**
 * @author: zhaoyi.wang
 * @description: 拉取未读消息响应
 **/
@Data
public class UnreadMessageResponseCommand extends AbstractCommand {

    /**
     * registrationId
     */
    private String userId;

    private Boolean status;

    private String errorMsg;

    private List<PushMessageVO> messages;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.UNREAD_MESSAGE_RES;
    }

    @Override
    public Boolean needCallback() {
        return false;
    }

    @Override
    public String getSenderId() {
        return null;
    }

}
