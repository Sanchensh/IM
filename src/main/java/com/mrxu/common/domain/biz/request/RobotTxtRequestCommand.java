package com.mrxu.common.domain.biz.request;

import com.mrxu.common.ImCommandCode;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: zhaoyi.wang
 * @date: 2019-02-19 10:02
 * @description:
 */
@Data
public class RobotTxtRequestCommand extends TxtRequestCommand {

    /**
     * 转人工标识：
     * “TransferToKf”表示转人工，
     * “Robot”非转人工（也就是机器人继续回 答）
     */
    private String transfer;

    /**
     * 问答类型：
     * 1、表示存在最佳匹配单条答案；
     * 2、表示不存在最佳匹配，存在相似问题或者菜单 答案；
     * 3、表示不存在匹配答案，返回默认回答
     */
    private String resultType;

    /**
     * 菜单项, 当resultType 为 2 时,此参数有值
     */
    private List<String> similarQuestions;

    /**
     * 访客意图信息
     */
    private List<BillInfo> billInfos;

    @Data
    public static class BillInfo implements Serializable {

        /**
         * 运单号
         */
        private String billCode;

        /**
         * 用户名
         */
        private String visitor;

        /**
         * 电话
         */
        private String phone;

        /**
         * 客户身份(sender:发件人；reciever:收件人)
         */
        private String identity;

        /**
         * 意图
         */
        private List<String> intentions;

    }

    @Override
    public ImCommandCode getCmdCode() {
        return ImCommandCode.ROBOT_TXT_REQ;
    }

}
