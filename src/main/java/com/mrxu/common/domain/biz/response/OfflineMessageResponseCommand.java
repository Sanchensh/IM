package com.mrxu.common.domain.biz.response;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import com.mrxu.common.domain.biz.vo.OfflineMessageVO;
import lombok.Data;

import java.util.List;

/**
 * @author: zhaoyi.wang
 * @date: 2019-02-12 13:31
 * @description: 未读消息响应
 */
@Data
public class OfflineMessageResponseCommand extends AbstractCommand {

    // 消息类型 0: 离线消息 1：历史消息
    private short type;

    private String userId;

    private Boolean status;

    private String errorMsg;

    private List<OfflineMessageVO> messages;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.OFFLINE_MESSAGE_RES;
    }

    @Override
    public String getSenderId() {
        return null;
    }

}
