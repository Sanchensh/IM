package com.mrxu.common.domain.files.request;

import java.util.List;
import java.util.Map;

/**
 * in body param
 *
 * @author feng.chuang
 * @date 2021-04-25 13:56
 */
public class RequestBody {
    /**
     * 文件名
     */
    private String fileName;


    /**
     * 类型:folder - 文件夹
     */
    private String fileType;

    /**
     * 文件名冲突行为 : fail - 返回失败。rename - 重命名。
     */
    private String fileNameConflictBehavior;

    /**
     * 如果父目录不存在，是否创建之
     */
    private boolean parents;


    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件 hash 值
     */
    private Map<String,String> fileHashes;

    /**
     * 上传请求方法 Default: PUT Enum:PUT POST
     */
    private String uploadMethod;

    /**
     * 调用方回传给服务器的信息
     */
    private String feedback;

    /**
     * Enum:committed aborted 事务状态
     */
    private String status;

    /**
     * 要处理的文档后缀
     */
    private String fileExtension;

    /**
     * 要处理的文档二进制流
     */
    private String fileContent;

    /**
     * 文档下载地址
     */
    private String location;

    /**
     * 会话文件操作步骤:可选值为deleteComments,acceptRevisions,convertToPDF,convertToTXT
     */
    private List<Operate> operates;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileNameConflictBehavior() {
        return fileNameConflictBehavior;
    }

    public void setFileNameConflictBehavior(String fileNameConflictBehavior) {
        this.fileNameConflictBehavior = fileNameConflictBehavior;
    }

    public boolean isParents() {
        return parents;
    }

    public void setParents(boolean parents) {
        this.parents = parents;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getUploadMethod() {
        return uploadMethod;
    }

    public void setUploadMethod(String uploadMethod) {
        this.uploadMethod = uploadMethod;
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

    public Map<String, String> getFileHashes() {
        return fileHashes;
    }

    public void setFileHashes(Map<String, String> fileHashes) {
        this.fileHashes = fileHashes;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Operate> getOperates() {
        return operates;
    }

    public void setOperates(List<Operate> operates) {
        this.operates = operates;
    }

    public class Operate{
        private String operate;

        private Map<String,Object> args;

        public String getOperate() {
            return operate;
        }

        public void setOperate(String operate) {
            this.operate = operate;
        }

        public Map<String, Object> getArgs() {
            return args;
        }

        public void setArgs(Map<String, Object> args) {
            this.args = args;
        }
    }

    public Operate newOperate(String operateStr){
        Operate operate = new Operate();
        operate.setOperate(operateStr);
        return operate;
    }
}
