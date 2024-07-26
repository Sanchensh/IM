package com.mrxu.common.domain.relationship;

/**
 * 组织架构结果
 *
 * @author feng.chuang
 * @date 2021-06-04 14:24
 */
public class DeptInfoResult<T> {
    private T result;
    private String code;
    private String message;
    private Boolean status;

    private Boolean hasMore;

    public DeptInfoResult() {
    }

    public DeptInfoResult(T result, Boolean status) {
        this.result = result;
        this.status = status;
    }

    public DeptInfoResult(T result, String message, Boolean status) {
        this.result = result;
        this.message = message;
        this.status = status;
    }

    public DeptInfoResult(T result, String code, String message, Boolean status) {
        this.result = result;
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public static DeptInfoResult error(String code, String message) {
        return new DeptInfoResult(null, code, message, false);
    }

    public DeptInfoResult(T result, Boolean status, Boolean hasMore) {
        this.result = result;
        this.status = status;
        this.hasMore = hasMore;
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

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }
}
