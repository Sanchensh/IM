package com.mrxu.common.enums;

/**
 * group type
 *
 * @author feng.chuang
 * @date 2021-06-02 10:49
 **/
public enum GroupType {
    /**
     * 普通群
     */
    ORDINARY_GROUP((byte) 0, "普通群"),

    /**
     * 内部群
     */
    INTERNAL_GROUP((byte) 1, "内部群"),

    /**
     * 部门群
     */
    DEPARTMENT_GROUP((byte) 2, "部门群"),

    /**
     * 服务群
     */
    SERVICE_GROUP((byte) 4, "服务群"),

    /**
     * 项目群
     */
    PROJECT_GROUP((byte) 8, "项目群");

    /**
     * 群聊类型
     */
    private Byte type;

    /**
     * 群聊类型说明
     */
    private String message;

    GroupType(Byte type, String message) {
        this.type = type;
        this.message = message;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
