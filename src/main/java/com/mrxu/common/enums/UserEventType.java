package com.mrxu.common.enums;

/**
 * @author: zhaoyi.wang
 * @date: 2020/3/5
 * @descriptin:
 **/
public enum UserEventType {

    // 上线
    ONLINE("online"),
    // 离线
    OFFLINE("offline"),
    // 添加群用户
    ADD("add"),
    // 删除群用户
    DELETE("delete"),
    // 解散群
    DISSOLVE("dissolve"),
    //新增用户
    CREATE_USER("createUser"),
    //更新用户(修改了real_name)
    UPDATE_USER("updateUser"),
    //删除用户
    DELETE_USER("deleteUser")
    ;

    private String type;

    UserEventType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
