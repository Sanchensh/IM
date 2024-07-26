package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

import java.util.Date;

/**
 * @Description: 转人工请求
 * @author: ztowh
 * @Date: 2018-12-25 13:32
 */
@Data
public class TransferRequireCommand extends AbstractCommand {

    /*
     * 客户的id
     */
    private String userId;

    private Date eventTime;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.TRANSFER_REQUIRE_EVENT;
    }

    @Override
    public String getSenderId() {
        return null;
    }
}
