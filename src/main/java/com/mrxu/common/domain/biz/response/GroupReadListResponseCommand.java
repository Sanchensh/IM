package com.mrxu.common.domain.biz.response;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import com.mrxu.common.domain.biz.vo.GroupReadListVO;
import lombok.Data;

import java.util.List;

/**
 * @author: zhaoyi.wang
 * @date: 2019/12/11 9:34 上午
 * @description:
 */
@Data
public class GroupReadListResponseCommand extends AbstractCommand {

    private boolean status;
    /**
     * 错误信息
     */
    private String errorMsg;
    /**
     * 状态码
     * S001 正常响应
     * S002 参数不合法
     * S302 不支持已读列表(超过限制)
     * S500 服务端异常
     * S503 服务降级
     */
    private String code;
    private List<GroupReadListVO> data;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.GROUP_READ_LIST_RES;
    }

    @Override
    public String getUserId() {
        return null;
    }

    @Override
    public String getSenderId() {
        return null;
    }
}
