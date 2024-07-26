package com.mrxu.common.enums;

/**
 * 用户信息相关响应状态码
 *
 * @author feng.chuang
 * @date 2021-06-01 09:51
 */
public enum UserInfoRespCode {

    /**
     * 成功
     */
    SUCCESS("0","成功"),

    /**
     * 未知异常
     */
    UNKNOWN_ERROR("1", "未知错误"),

    /**
     * 系统异常
     */
    SYSTEM_ERROR("2", "系统异常"),

    /**
     * 参数校验不合法
     */
    PARAM_VALID_FAILED("30001","参数校验不合法,"),

    /**
     * 生成头像时发生异常
     */
    AVATAR_ERROR("30002","生成头像时发生异常 "),

    /**
     * 要查询的用户不存在
     */
    USER_DONT_EXIST("30003","查询的用户不存在"),

    /**
     * 要查询的用户已被删除
     */
    USER_HAS_BEEN_DELETED("30004","查询的用户已被删除"),

    /**
     * 查询部门信息时，部门信息不完整，查询结果有null存在，可能是是部门信息不存在，也可能是部门已被删除
     */
    DEPARTMENT_INFO_INCOMPLETE("30005","查询部门信息时，得到的结果不完整"),

    /**
     * 手机号格式不正确
      */
    PHONE_NUMBER_FORMAT_INCORRECT("30006","手机号格式不正确 "),

    SYNC_COUNT_EXCEED_LIMIT("30007","批量同步数据数量超过限制,"),

    DEPT_NOT_EXIST("30008","部门不存在"),

    VALID_USER_NOT_EXIST("30009","校验过后合法的用户数量为0,本次新增用户数量为0"),

    /**
     * 批量插入，部分用户校验失败，不新增，合法的部分插入
     */
    PART_USER_INFO_INVALID("30010","批量插入时，部分用户校验信息不合法"),

    USERS_ADD_HAS_ALL_EXIST("30011","新增的用户均已存在"),




    ;


    /**
     * 状态码
     */
    private String code;

    /**
     * 响应信息
     */
    private String message;

    UserInfoRespCode(String code, String message) {
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
