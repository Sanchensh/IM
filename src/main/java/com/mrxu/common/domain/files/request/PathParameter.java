package com.mrxu.common.domain.files.request;

/**
 * in path param，第三方存储入参
 *
 * @author feng.chuang
 * @date 2021-04-25 13:54
 */
public class PathParameter {
    /**
     * 卷标识，卷唯一Id
     */
    private String volume;

    /**
     * 文件标识，当值为root时表示根文件夹
     * 其他的都是传的文件唯一ID,不论文件夹还是文件,都有一个唯一ID
     */
    private String file;

    /**
     * 事务标识
     */
    private String transaction;

    /**
     * 公司标识
     */
    private String company;

    /**
     * sessionId
     */
    private String sessionId;

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
