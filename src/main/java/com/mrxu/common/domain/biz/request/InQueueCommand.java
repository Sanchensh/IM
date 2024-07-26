package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2019-01-10 13:42
 */
@Data
public class InQueueCommand extends AbstractCommand {

    /*
     * 客户的id
     */
    private String userId;

    /*
     * 技能组id
     */
    private String serviceGroupId;
    /*
     * 排队数量
     */
    private Integer queueNum;

    /*
     * 展示信息
     */
    private String msg;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.INQUEUE_REQ;
    }

    @Override
    public String getSenderId() {
        return null;
    }
}
