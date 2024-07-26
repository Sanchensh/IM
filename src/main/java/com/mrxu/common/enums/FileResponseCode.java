package com.mrxu.common.enums;

/**
 * 文件相关操作响应状态码
 *
 * @author feng.chuang
 * @date 2021-05-08 09:40
 **/
public enum FileResponseCode {
    /**
     * 成功
     */
    SUCCESS("0", "成功"),

    /**
     * 未知异常
     */
    UNKNOWN_ERROR("1", "未知错误"),

    /**
     * 系统异常
     */
    SYSTEM_ERROR("2", "系统异常"),

    /**
     * 创建上传事务异常
     */
    CREATE_TRANSACTION_ERROR("20001", "创建上传事务失败，请重新创建上传事务"),


    /**
     * 创建事务第三方响应为null
     */
    CREATE_TRANSACTION_NULL("20002", "创建上传事务第三方响应为null，请重新创建上传事务"),

    /**
     * 事务提交异常
     */
    COMMIT_TRANSACTION_ERROR("20003", "提交事务失败，请重新上传文件"),


    /**
     * 事务提交第三方响应结果为空
     */
    COMMIT_TRANSACTION_NULL("20004", "提交事务第三方响应为null，请重新上传文件"),

    /**
     * 文件已被删除
     */
    FILE_DELETED("20005", "文件已被删除"),

    /**
     * 用户不属于当前群组
     */
    USER_NOT_IN_GROUP("20006", "用户不属于当前群组"),

    /**
     * 删除文件，创建人，群主，群管理员才有权限删除
     */
    NO_PERMISSION_TO_DELETE("20007", "无权删除文件"),

    /**
     * 文件格式不支持预览
     */
    DONT_SUPPORT_PREVIEW("20008", "此文件格式不支持预览"),

    /**
     * 文件格式不支持在线编辑
     */
    DONT_SUPPORT_EDIT("20009", "此文件格式不支持在线编辑"),

    FILE_NOT_EXIST("20010","文件不存在"),
    ;

    private String code;

    private String message;

    FileResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
