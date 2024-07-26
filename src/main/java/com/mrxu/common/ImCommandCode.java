package com.mrxu.common;

import com.alibaba.fastjson.serializer.JSONSerializable;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.mrxu.common.domain.biz.request.*;
import com.mrxu.common.domain.biz.response.*;

import java.io.IOException;
import java.lang.reflect.Type;

public enum ImCommandCode implements JSONSerializable {

    AUTH_REQ((short) 1, AuthRequestCommand.class.getName()),
    AUTH_RES((short) 2, AuthResponseCommand.class.getName()),
    TXT_REQ((short) 3, TxtRequestCommand.class.getName()),
    TXT_RES((short) 4, CommonResponseCommand.class.getName()),
    // 评价请求
    EVALUATION_REQ((short) 5, EvaluationCommand.class.getName()),
    EVALUATION_RESULT((short) 6, EvaluationResultCommand.class.getName()),
    DOC_REQ((short) 7, DocRequestCommand.class.getName()),
    ASSIGN_REQ((short) 8, AssignCommand.class.getName()),
    INQUEUE_REQ((short) 9, InQueueCommand.class.getName()),
    REJECT_REQ((short) 10, RejectCommand.class.getName()),
    CONNECT_EVENT((short) 12, ConnectionEstablishCommand.class.getName()),
    CLOSE_EVENT((short) 13, ConnectionCloseCommand.class.getName()),
    // 客户端要求转人工
    TRANSFER_REQUIRE_EVENT((short) 14, TransferRequireCommand.class.getName()),
    // 客服端分配到人或者组的事件
    TRANSFER_RESULT_EVENT((short) 15, TransferResultCommand.class.getName()),
    // 客服转客服请求响应
    TRANSFER_AGENT_RESULT((short) 16, TransferAgentResultCommand.class.getName()),
    // 客户端sdk发出的会话超时事件
    CONVERSATION_TIMEOUT_EVENT((short) 17, ConversationCloseCommand.class.getName()),
    // 客户端sdk发出的会话即将超时事件
    PRE_CONVERSATION_TIMEOUT_EVENT((short) 18, PreConversationTimeoutCommand.class.getName()),
    QUEUE_NUM_REQ((short) 19, QueueNumRequestCommand.class.getName()),
    // 获取聊天消息请求
    OFFLINE_MESSAGE_REQ((short) 20, OfflineMessageRequestCommand.class.getName()),
    OFFLINE_MESSAGE_RES((short) 21, OfflineMessageResponseCommand.class.getName()),
    // 未读消息已读回执
    OFFLINE_MESSAGE_READ_REQ((short) 22, OfflineMessageReadRequestCommand.class.getName()),
    OFFLINE_MESSAGE_READ_RES((short) 23, OfflineMessageReadResponseCommand.class.getName()),
    // 客服转接请求
    TRANSFER_AGENT_REQ((short) 24, TransferAgentRequestCommand.class.getName()),
    ROBOT_TXT_REQ((short) 25, RobotTxtRequestCommand.class.getName()),

    IN_QUEUE_TIMEOUT_REQ((short) 26, InQueueTimeoutRequestCommand.class.getName()),
    IN_QUEUE_TIMEOUT_RESULT((short) 11, InQueueTimeoutResultCommand.class.getName()),
    // 首次响应命令
    FIRST_RESPONSE((short) 27, FirstResponseCommand.class.getName()),
    // 接起会话命令
    PICK_UP_CONVERSATION((short) 28, PickUpConversationCommand.class.getName()),
    // 机器人会话关闭
    ROBOT_CONVERSATION_CLOSE((short) 29, RobotConversationCloseCommand.class.getName()),

    OFFLINE_RES((short) 98, OfflineResponseCommand.class.getName()),
    ERROR_RES((short) 99, ErrorResponseCommand.class.getName()),

    EVALUATION_RESULT_RES((short) 999, EvaluationResultResponseCommand.class.getName()),
    DOC_RES((short) 998, DocResponseCommand.class.getName()),
    TRANSFER_AGENT_RESULT_RES((short) 997, TransferAgentResultResponseCommand.class.getName()),
    CONVERSATION_TIMEOUT_RES((short) 996, ConversationCloseResponseCommand.class.getName()),
    QUEUE_NUM_RES((short) 995, QueueNumResponseCommand.class.getName()),
    OFFLINE_MESSAGE_ACK((short) 994, OfflineMessageRequestAckCommand.class.getName()),
    TRANSFER_AGENT_RES((short) 993, TransferAgentResponseCommand.class.getName()),
    IN_QUEUE_TIMEOUT_RESULT_RES((short) 992, InQueueTimeoutResultResponseCommand.class.getName()),
    FIRST_RESPONSE_RES((short) 991, FirstResponseAckCommand.class.getName()),
    PICK_UP_CONVERSATION_RES((short) 990, PickUpConversationResponseCommand.class.getName()),
    EVALUATION_RES((short) 989, EvaluationResponseCommand.class.getName()),
    CALLBACK_REQ((short) 988, CallbackRequestCommand.class.getName()),
    CALLBACK_RES((short) 987, CallbackResponseCommand.class.getName()),

    PUSH_MESSAGE((short) 1001, PushTxtCommand.class.getName()),
    PUSH_MESSAGE_RES((short) 1002, PushTxtResponseCommand.class.getName()),
    REGISTER_REQ((short) 1003, RegisterCommand.class.getName()),
    REGISTER_RES((short) 1004, RegisterResponseCommand.class.getName()),
    UNREGISTER_REQ((short) 1005, UnregisterCommand.class.getName()),
    UNREGISTER_RES((short) 1006, UnregisterResponseCommand.class.getName()),
    // 拉取未读消息请求
    UNREAD_MESSAGE_REQ((short) 1007, UnreadMessageCommand.class.getName()),
    // 拉取未读消息响应
    UNREAD_MESSAGE_RES((short) 1008, UnreadMessageResponseCommand.class.getName()),
    // 收到未读消息ack
    UNREAD_MESSAGE_ACK((short) 1009, UnreadMessageAckCommand.class.getName()),
    // 收取到未读消息ack响应
    UNREAD_MESSAGE_ACK_RES((short) 1010, UnreadMessageAckResponseCommand.class.getName()),
    // 消息撤回请求
    RECALL_MESSAGE_REQ((short) 1011, RecallMessageRequestCommand.class.getName()),
    RECALL_MESSAGE_RES((short) 1012, RecallMessageResponseCommand.class.getName()),
    // 消息已读请求
    MESSAGE_READ_REQ((short) 1013, MessageReadRequestCommand.class.getName()),
    MESSAGE_READ_RES((short) 1014, MessageReadResponseCommand.class.getName()),
    // 离线消息拉取
    IM_OFFLINE_MSG_REQ((short) 1015, ImOfflineMessageRequestCommand.class.getName()),
    IM_OFFLINE_MSG_RES((short) 1016, ImOfflineMessageResponseCommand.class.getName()),
    // 消息免打扰命令
    MUTE_NOTIFICATION_REQ((short) 1017, MuteNotificationRequestCommand.class.getName()),
    MUTE_NOTIFICATION_RES((short) 1018, MuteNotificationResponseCommand.class.getName()),
    // IM 消息
    MESSAGE_REQ((short) 1019, MessageRequestCommand.class.getName()),
    MESSAGE_RES((short) 1020, MessageResponseCommand.class.getName()),
    // 通知类消息命令
    NOTIFICATION_REQ((short) 1021, NotificationRequestCommand.class.getName()),
    NOTIFICATION_RES((short) 1022, NotificationResponseCommand.class.getName()),
    // 群消息已读列表
    GROUP_READ_LIST_REQ((short) 1023, GroupReadListRequestCommand.class.getName()),
    GROUP_READ_LIST_RES((short) 1024, GroupReadListResponseCommand.class.getName()),
    // 功能消息已读列表
    FUNCTIONAL_MESSAGE_REQ((short) 1025, FunctionalMessageRequestCommand.class.getName()),
    FUNCTIONAL_MESSAGE_RES((short) 1026, FunctionalMessageResponseCommand.class.getName()),
    // 功能消息列表
    FUNCTIONAL_MESSAGE_LIST_REQ((short) 1027, FunctionalMessageListRequestCommand.class.getName()),
    FUNCTIONAL_MESSAGE_LIST_RES((short) 1028, FunctionalMessageListResponseCommand.class.getName()),

    // web端推送
    WEB_PUSH_REQ((short) 1029, WebPushRequestCommand.class.getName()),
    WEB_PUSH_RES((short) 1030, WebPushResponseCommand.class.getName()),

    // rtc消息
    RTC_MESSAGE_REQ((short) 1031, RTCMessageRequestCommand.class.getName()),
    RTC_MESSAGE_RES((short) 1032, RTCMessageResponseCommand.class.getName()),

    // 删除消息
    DELETE_MESSAGE_REQ((short) 1033, DeleteMessageRequestCommand.class.getName()),
    DELETE_MESSAGE_RES((short) 1034, DeleteMessageResponseCommand.class.getName()),

    // 已阅消息
    MESSAGE_OPENED_REQ((short) 1035, MessageOpenedRequestCommand.class.getName()),
    MESSAGE_OPENED_RES((short) 1036, MessageOpenedResponseCommand.class.getName()),

    // 已阅消息列表
    MESSAGE_READER_LIST_REQ((short) 1037, MessageReaderListRequestCommand.class.getName()),
    MESSAGE_READER_LIST_RES((short) 1038, MessageReaderListResponseCommand.class.getName()),

    // 修改消息
    MESSAGE_MODIFY_REQ((short) 1039, MessageModifyRequestCommand.class.getName()),
    MESSAGE_MODIFY_RES((short) 1040, MessageModifyResponseCommand.class.getName()),
    ;

    private short value;
    private String className;

    ImCommandCode(short value, String className) {
        this.value = value;
        this.className = className;
    }

    public short value() {
        return this.value;
    }

    public String clazz() {
        return this.className;
    }

    public static ImCommandCode valueOf(short value) {
        ImCommandCode[] values = ImCommandCode.values();
        for (ImCommandCode command : ImCommandCode.values()) {
            if (command.value == value) {
                return command;
            }
        }
        throw new IllegalArgumentException("Unknown Rpc command code value: " + value);
    }

    public static String classOf(short value) {
        ImCommandCode[] values = ImCommandCode.values();
        for (ImCommandCode command : ImCommandCode.values()) {
            if (command.value == value) {
                return command.className;
            }
        }
        throw new IllegalArgumentException("Unknown Rpc command code value: " + value);
    }

    @Override
    public void write(JSONSerializer serializer, Object fieldName, Type fieldType, int features) throws IOException {
        serializer.write(value);
    }

}
