package com.mrxu.common.enums;

public interface PushLevel {

    /**
     * 消息推送级别
     * 0: 不推送
     * 1: 离线推送
     * 2: 免打扰离线推送
     * 3: 设备免打扰离线推送
     */

    /**
     * 不推送
     */
    Byte NO_PUSH = 0;
    /**
     * 离线推送
     */
    Byte OFFLINE_PUSH = 1;
    /**
     * 免打扰推送
     */
    Byte MUTE_PUSH = 2;
    /**
     * 设备免打扰推送
     */
    Byte DEVICE_MUTE_PUSH = 3;
}
