package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.AbstractCommand;
import lombok.Data;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2018/12/5 13:31
 */
@Data
public class DocRequestCommand extends AbstractCommand {

    private String senderId;
    private String recieverId;
    /**
     * 1: 图片
     * 2: 文件
     * 3: 视频
     */
    private String type;
    private String docUrl;
    private String content;
    /**
     * 是否群组消息
     */
    private boolean group;
    private String groupId;
    private long parentMsgId;
    private boolean disableDeliver;

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.DOC_REQ;
    }

    @Override
    public String getUserId() {
        return this.recieverId;
    }

}
