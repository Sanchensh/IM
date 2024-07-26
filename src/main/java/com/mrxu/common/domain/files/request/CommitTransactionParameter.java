package com.mrxu.common.domain.files.request;

import java.util.Map;

/**
 * 提交事务参数，第三方存储入参
 *
 * @author feng.chuang
 * @date 2021-04-26 10:10
 */
public class CommitTransactionParameter {

    /**
     * 文件hash值，key有2种情况，sha1和md5
     */
    private Map<String, String> fileHashes;

    /**
     * 调用方回传给服务器的信息
     */
    private String feedback;

    /**
     * 事务状态,枚举值
     * commited - 提交。
     * aborted - 中止。
     */
    private String status;

    public Map<String, String> getFileHashes() {
        return fileHashes;
    }

    public void setFileHashes(Map<String, String> fileHashes) {
        this.fileHashes = fileHashes;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
