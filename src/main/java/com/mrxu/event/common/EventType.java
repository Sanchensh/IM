package com.mrxu.event.common;

public enum EventType {

    ASSIGN_EVENT("ASSIGN", AssignEvent.class),
    IN_QUEUE_EVENT("IN_QUEUE", InQueueEvent.class),
    // 排队超时事件
    IN_QUEUE_TIMEOUT_RESULT_EVENT("IN_QUEUE_TIMEOUT", InQueueTimeoutResultEvent.class),
    REJECT_EVENT("REJECT", RejectEvent.class),
    NOTIFY_EVENT("NOTIFY", NotifyEvent.class),
    CONNECT_EVENT("CONNECT", ConnectionEstablishEvent.class),
    CLOSE_EVENT("CLOSE", ConnectionCloseEvent.class),
    // 客户端要求转人工
    TRANSFER_REQUIRE_EVENT("TRANSFER_AGENT", TransferRequireEvent.class),
    // 客服端分配到人或者组的事件
    // 客服转客服，响应结果
    TRANSFER_RESULT_EVENT("TRANSFER", TransferResultEvent.class),

    CONNECT_TIMEOUT_EVENT("CONNECT_TIMEOUT", ConnectionTimeoutEvent.class),
    // 客户端sdk发出的会话超时事件
    CONVERSATION_TIMEOUT_EVENT("SESSION_TIMEOUT", ConversationTimeoutEvent.class),
    // 客户端sdk发出的会话即将超时事件
    PRE_CONVERSATION_TIMEOUT_EVENT("PRE_SESSION_TIMEOUT", PreConversationTimeoutEvent.class),
    // 评价结果事件
    EVALUATION_RESULT_EVENT("EVALUATION_RESULT", EvaluationResultEvent.class),
    // 首次响应事件
    FIRST_RESPONSE_EVENT("FIRST_RESPONSE", FirstResponseEvent.class),
    PICK_UP_CONVERSATION_EVENT("PICK_UP_CONVERSATION", PickUpConversationEvent.class),
    // 机器人会话关闭事件
    ROBOT_CONVERSATION_CLOSE_EVENT("ROBOT_CONVERSATION_CLOSE", RobotConversationCloseEvent.class),
    ;

    private String type;
    private Class clazz;

    EventType(String type, Class<? extends Event> clazz) {
        this.type = type;
        this.clazz = clazz;
    }

    public String getType() {
        return type;
    }

    public Class<? extends Event> getClazz() {
        return clazz;
    }

    public static EventType getEventType(String type) {
        for (EventType eventType : EventType.values()) {
            if (eventType.getType().equals(type)) {
                return eventType;
            }
        }
        throw new IllegalArgumentException("Unknown EventType: " + type);
    }

    public static Class<? extends Event> getClazz(String type) {
        for (EventType eventType : EventType.values()) {
            if (eventType.getType().equals(type)) {
                return eventType.getClazz();
            }
        }
        throw new IllegalArgumentException("Unknown EventType: " + type);
    }

}
