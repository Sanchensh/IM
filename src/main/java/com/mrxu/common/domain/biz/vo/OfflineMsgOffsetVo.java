package com.mrxu.common.domain.biz.vo;

import java.io.Serializable;

/**
 * 离线消息偏移量聚合对象,用于返回给前台
 *
 * @author feng.chuang
 * @date 2021-04-09 14:48
 */
public class OfflineMsgOffsetVo implements Serializable {
    private static final long serialVersionUID = 8688272509462582204L;
    /**
     * 消息偏移量
     */
    private String offsetId;

    /**
     * 消息聚合对象，包含消息修改
     */
    private ImMessageVO imMessageVO;

    public String getOffsetId() {
        return offsetId;
    }

    public void setOffsetId(String offsetId) {
        this.offsetId = offsetId;
    }

    public ImMessageVO getImMessageVO() {
        return imMessageVO;
    }

    public void setImMessageVO(ImMessageVO imMessageVO) {
        this.imMessageVO = imMessageVO;
    }
}
