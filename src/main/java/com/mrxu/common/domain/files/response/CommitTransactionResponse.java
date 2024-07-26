package com.mrxu.common.domain.files.response;

import java.util.Map;

/**
 * 提交事务响应
 *
 * @author feng.chuang
 * @date 2021-04-28 17:19
 */
public class CommitTransactionResponse {
    private String id;
    private String storage;
    private Map<String,Object> fileHashes;
    private String status;
    private ThirdPartyFileResponse thirdPartyFileResponse;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public Map<String, Object> getFileHashes() {
        return fileHashes;
    }

    public void setFileHashes(Map<String, Object> fileHashes) {
        this.fileHashes = fileHashes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ThirdPartyFileResponse getFileResponse() {
        return thirdPartyFileResponse;
    }

    public void setFileResponse(ThirdPartyFileResponse thirdPartyFileResponse) {
        this.thirdPartyFileResponse = thirdPartyFileResponse;
    }
}
