package com.mrxu.common.domain.relationship;

/**
 * 群组信息响应结果
 *
 * @author feng.chuang
 * @date 2021-06-02 09:28
 */
public class GroupInfoResult<T> {
    private T result;
    private String code;
    private String message;
    private Boolean status;

    private Boolean hasMore;

    public GroupInfoResult() {
    }

    public GroupInfoResult(T result, Boolean status) {
        this.result = result;
        this.status = status;
    }

    public GroupInfoResult(T result, String message, Boolean status) {
        this.result = result;
        this.message = message;
        this.status = status;
    }

    public GroupInfoResult(T result, String code, String message, Boolean status) {
        this.result = result;
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public static GroupInfoResult error(String code, String message) {
        return new GroupInfoResult(null, code, message, false);
    }

    public GroupInfoResult(T result, Boolean status, Boolean hasMore) {
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
