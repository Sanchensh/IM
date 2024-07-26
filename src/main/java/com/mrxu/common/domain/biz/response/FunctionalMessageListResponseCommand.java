package com.mrxu.common.domain.biz.response;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import com.mrxu.common.domain.biz.vo.FunctionalMessageVO;


import java.util.List;

public class FunctionalMessageListResponseCommand extends AbstractCommand {

    private Boolean status;

    private String code;

    private String errorMsg;

    private List<FunctionalMessageVO> data;

    private Boolean hasMore;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<FunctionalMessageVO> getData() {
        return data;
    }

    public void setData(List<FunctionalMessageVO> data) {
        this.data = data;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    @Override
    public String getUserId() {
        return null;
    }

    @Override
    public String getSenderId() {
        return null;
    }

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.FUNCTIONAL_MESSAGE_LIST_RES;
    }

}
