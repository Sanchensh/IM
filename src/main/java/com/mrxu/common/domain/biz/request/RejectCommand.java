package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @Description: 拒接命令
 * @author: ztowh
 * @Date: 2019-01-10 13:47
 */
@Data
public class RejectCommand extends AbstractCommand {

    /*
     * 客户的id
     */
    private String userId;
    /*
     * 拒接code
     */
    private String code;
    /*
     * 拒接详情
     */
    private String msg;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.REJECT_REQ;
    }

    @Override
    public String getSenderId() {
        return null;
    }
}
