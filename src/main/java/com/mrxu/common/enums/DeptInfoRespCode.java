package com.mrxu.common.enums;

/**
 * dept resp code
 *
 * @author feng.chuang
 * @date 2021-06-04 14:40
 */
public enum DeptInfoRespCode {

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
    PARAM_VALID_FAILED("50001","参数校验不合法,"),

    /**
     * 获取上级部门时失败
     */
    GET_LEVEL_FAILED("50002",""),

    /**
     * 新增组织时，组织已存在
     */
    DEPT_HAS_EXIST("50003","新增组织时，组织已存在"),

    /**
     * 不允许code=parentCode
     */
    PARENT_EQUALS_CODE_NOT_PERMITTED("50004","不允许code=parentCode"),

    /**
     * 删除一个部门时，如果存在下级部门，则需要先修改下级部门的上级部门
     */
    DELETE_DEPT_EXIST_SUB("50005","删除部门时，此部门存在下级部门，请先修改下级部门的父级部门"),

    /**
     * 更新上级部门时，传过来的parentCode和表里已存在的相等
     */
    PARENT_CODE_TO_UPDATE_EQUALS_EXIST("50006","更新上级部门时，要更新的parentCode和当前的parentCode相等"),
    ;


    /**
     * 状态码
     */
    private String code;

    /**
     * 响应信息
     */
    private String message;

    DeptInfoRespCode(String code, String message) {
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
