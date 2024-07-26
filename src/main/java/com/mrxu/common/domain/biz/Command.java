package com.mrxu.common.domain.biz;

import com.alibaba.fastjson2.annotation.JSONField;
import com.mrxu.common.ImCommandCode;

import java.io.Serializable;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2018/11/28 16:59
 */
public interface Command extends Serializable {

    @JSONField(serialize = false)
    ImCommandCode getCmdCode();

    @JSONField(serialize = false)
    Boolean needCallback();

    String getUserId();

    String getSenderId();
}
