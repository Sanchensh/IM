package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

import java.util.Date;

/**
 * @Description: 客服互转请求（客服端sdk发出）
 * @author: ztowh
 * @Date: 2018-12-25 13:33
 */
@Data
public class TransferResultCommand extends AbstractCommand {

    /*
     * 客户的id
     */
    private String userId;

    private Date eventTime;

    /*
     * 1：人，2：组
     */
    private String type;
    /*
     * 客服或组id
     */
    private String serviceId;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.TRANSFER_RESULT_EVENT;
    }

    @Override
    public String getSenderId() {
        return null;
    }
}
