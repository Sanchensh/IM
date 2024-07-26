package com.mrxu.common.domain.biz.response;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import com.mrxu.common.domain.biz.vo.ImMessageVO;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author: zhaoyi.wang
 **/
@Data
@ToString
public class ImOfflineMessageResponseCommand extends AbstractCommand {

    private String userId;

    private Boolean status;

    private String errorMsg;

    private List<ImMessageVO> messages;

    private Boolean hasMore;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.IM_OFFLINE_MSG_RES;
    }

    @Override
    public String getSenderId() {
        return null;
    }

}
