package com.mrxu.common.domain.files.response;

import com.mrxu.common.domain.biz.vo.VolumeVo;

/**
 * volume
 *
 * @author feng.chuang
 * @date 2021-04-25 17:21
 */
public class VolumeResponse {
    private VolumeVo[] value;
    private String next;

    public VolumeVo[] getValue() {
        return value;
    }

    public void setValue(VolumeVo[] value) {
        this.value = value;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
