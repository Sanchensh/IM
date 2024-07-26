package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @Description: 分配客服的命令
 * @author: ztowh
 * @Date: 2019-01-10 13:34
 */
@Data
public class AssignCommand extends AbstractCommand {

    /*
     * 客户的id
     */
    private String userId;

    /*
     * 分配的客服的id
     */
    private String csId;

    /*
     * 分配客服需要展示的消息
     */
    private String msg;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.ASSIGN_REQ;
    }

    @Override
    public String getSenderId() {
        return null;
    }
}
