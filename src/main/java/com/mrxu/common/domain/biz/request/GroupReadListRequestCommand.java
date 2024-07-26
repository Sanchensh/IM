package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @author: zhaoyi.wang
 * @date: 2019/12/11 9:32 上午
 * @description: 群消息已读列表
 */
@Data
public class GroupReadListRequestCommand extends AbstractCommand {

    private String senderId;

    private String groupId;

    @Override
    public String getUserId() {
        return null;
    }

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.GROUP_READ_LIST_REQ;
    }

    @Override
    public Boolean needCallback() {
        return false;
    }

}
