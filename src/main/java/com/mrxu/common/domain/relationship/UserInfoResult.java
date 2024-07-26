package com.mrxu.common.domain.relationship;

/**
 * user info response
 *
 * @author feng.chuang
 * @date 2021-05-27 15:47
 */
public class UserInfoResult<T> {

    private T result;
    private String code;
    private String message;
    private Boolean status;

    public UserInfoResult() {
    }

    public UserInfoResult(T result, Boolean status) {
        this.result = result;
        this.status = status;
    }

    public UserInfoResult(T result, String message, Boolean status) {
        this.result = result;
        this.message = message;
        this.status = status;
    }

    public UserInfoResult(T result, String code, String message, Boolean status) {
        this.result = result;
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public static UserInfoResult error(String code, String message) {
        return new UserInfoResult(null, code, message, false);
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
