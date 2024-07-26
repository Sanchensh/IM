package com.mrxu.common.domain.files.response;

import java.util.Collections;
import java.util.List;

/**
 * 会话空间文件列表结果
 *
 * @author feng.chuang
 * @date 2021-05-08 10:20
 */
public class ConversationFileResult<T> {
    private List<T> result;
    private String code;
    private String message;
    private Boolean status;
    private Boolean hasMore;

    public ConversationFileResult() {
    }

    public ConversationFileResult(List<T> result, String code, String message, Boolean status, Boolean hasMore) {
        this.result = result;
        this.code = code;
        this.message = message;
        this.status = status;
        this.hasMore = hasMore;
    }

    public ConversationFileResult(List<T> result, Boolean status, Boolean hasMore) {
        this.result = result;
        this.status = status;
        this.hasMore = hasMore;
    }

    public static ConversationFileResult empty() {
        return new ConversationFileResult(Collections.emptyList(), true, false);
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
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
