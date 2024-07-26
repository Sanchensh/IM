package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;

public class FunctionalMessageListRequestCommand extends AbstractCommand {

    /**
     * 用户Id
     */
    private String userId;
    /**
     * 功能类型(业务方自定义)
     * <p>
     * example:
     * <p>
     * 0: @功能
     * 1: ding
     * 2: 红包
     * 3: 收藏
     */
    private Byte type;

    /**
     * 0: 全部, 1: 未读, 2: 已读
     */
    private Byte status;

    /**
     * 最小消息ID, 返回小于msgId的数据
     */
    private Long minMsgId;

    /**
     * 最大消息ID, 返回大于msgId的数据
     */
    private Long maxMsgId;

    /**
     * 每页数量(最大200)
     */
    private Integer pageSize;

    /**
     * 查询时间范围(单位天),默认7天,最大365天
     */
    private Integer timeRange;

    @Override
    public String getSenderId() {
        return userId;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    public FunctionalMessageListRequestCommand setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Byte getType() {
        return type;
    }

    public FunctionalMessageListRequestCommand setType(Byte type) {
        this.type = type;
        return this;
    }

    public Byte getStatus() {
        return status;
    }

    public FunctionalMessageListRequestCommand setStatus(Byte status) {
        this.status = status;
        return this;
    }

    public Long getMinMsgId() {
        return minMsgId;
    }

    public FunctionalMessageListRequestCommand setMinMsgId(Long minMsgId) {
        this.minMsgId = minMsgId;
        return this;
    }

    public Long getMaxMsgId() {
        return maxMsgId;
    }

    public FunctionalMessageListRequestCommand setMaxMsgId(Long maxMsgId) {
        this.maxMsgId = maxMsgId;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public FunctionalMessageListRequestCommand setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public Integer getTimeRange() {
        return timeRange;
    }

    public FunctionalMessageListRequestCommand setTimeRange(Integer timeRange) {
        this.timeRange = timeRange;
        return this;
    }

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.FUNCTIONAL_MESSAGE_LIST_REQ;
    }

    @Override
    public Boolean needCallback() {
        return false;
    }
}
