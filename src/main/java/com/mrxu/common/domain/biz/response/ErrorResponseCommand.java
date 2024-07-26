package com.mrxu.common.domain.biz.response;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2018/12/3 14:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseCommand extends AbstractCommand {

    private String msg;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.ERROR_RES;
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
