package com.mrxu.common.domain.files.response;

/**
 * 文件通用响应信息
 *
 * @author feng.chuang
 * @date 2021-05-06 10:02
 */
public class FileCommonResult<T> {
    /**
     * 地址，过期时间，文件md5等信息
     */
    private T result;

    /**
     * 额外信息
     */
    private String message;

    /**
     * 状态码
     */
    private String code;

    /**
     * true/false 成功/失败
     */
    private Boolean status;

    public FileCommonResult() {

    }

    public FileCommonResult(T result, String message, Boolean status) {
        this.result = result;
        this.message = message;
        this.status = status;
    }

    public FileCommonResult(T result, String code, String message, Boolean status) {
        this.result = result;
        this.code = code;
        this.message = message;
        this.status = status;
    }


    public FileCommonResult(T result, Boolean status) {
        this.result = result;
        this.status = status;
    }

//    public static FileCommonResult success(Object result, Boolean status) {
//        return new FileCommonResult(result, true);
//    }

    public static FileCommonResult error(String code, String message) {
        return new FileCommonResult(null, code, message, false);
    }


    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
