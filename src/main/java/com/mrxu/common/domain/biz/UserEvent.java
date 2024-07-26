package com.mrxu.common.domain.biz;

import com.mrxu.common.domain.biz.vo.UserInfoCommonVo;
import com.mrxu.common.enums.UserEventType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author: zhaoyi.wang
 * @date: 2020/3/5
 * @descriptin:
 **/
public class UserEvent implements Serializable {

    /**
     * 用户ID
     */
    private List<String> userIds;

    /**
     * 群组ID
     */
    private String groupId;

    /**
     * 事件产生时间
     */
    private Date eventTime;

    /**
     * 上线/下线事件渠道
     */
    private Byte channel;

    /**
     * 事件产生IP
     */
    private String ipAddress;

    /**
     * 事件类型
     */
    private UserEventType eventType;

    /**
     * 进群第一条消息id
     */
    private Long firstMsgId;

    /**
     * 业务线
     */
    private String biz;

    private List<UserInfoCommonVo> userVoList;

    public List<String> getUserIds() {
        return userIds;
    }

    public UserEvent setUserIds(List<String> userIds) {
        this.userIds = userIds;
        return this;
    }

    public String getGroupId() {
        return groupId;
    }

    public UserEvent setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public UserEvent setEventTime(Date eventTime) {
        this.eventTime = eventTime;
        return this;
    }

    public UserEventType getEventType() {
        return eventType;
    }

    public UserEvent setEventType(UserEventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public Byte getChannel() {
        return channel;
    }

    public UserEvent setChannel(Byte channel) {
        this.channel = channel;
        return this;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public UserEvent setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public Long getFirstMsgId() {
        return firstMsgId;
    }

    public UserEvent setFirstMsgId(Long firstMsgId) {
        this.firstMsgId = firstMsgId;
        return this;
    }

    public String getBiz() {
        return biz;
    }

    public UserEvent setBiz(String biz) {
        this.biz = biz;
        return this;
    }

    public List<UserInfoCommonVo> getUserVoList() {
        return userVoList;
    }

    public UserEvent setUserVoList(List<UserInfoCommonVo> userVoList) {
        this.userVoList = userVoList;
        return this;
    }

    @Override
    public String toString() {
        return "UserEvent{" +
                "userIds=" + userIds +
                ", groupId='" + groupId + '\'' +
                ", eventTime=" + eventTime +
                ", channel=" + channel +
                ", ipAddress='" + ipAddress + '\'' +
                ", eventType=" + eventType +
                ", firstMsgId=" + firstMsgId +
                ", biz='" + biz + '\'' +
                ", userVoList=" + userVoList +
                '}';
    }
}
