package com.mrxu.common.enums;

/**
 * group resp code
 *
 * @author feng.chuang
 * @date 2021-06-02 10:09
 */
public enum GroupInfoRespCode {
    /**
     * 成功
     */
    SUCCESS("0","成功"),

    /**
     * 未知异常
     */
    UNKNOWN_ERROR("1", "未知错误"),

    /**
     * 系统异常
     */
    SYSTEM_ERROR("2", "系统异常"),

    /**
     * 参数校验不合法
     */
    PARAM_VALID_FAILED("40001","参数校验不合法,"),

    /**
     * 生成头像时发生异常
     */
    AVATAR_ERROR("40002","生成头像时发生异常"),

    /**
     * 用户不属于此群组
     */
    USER_DONT_BELONG_TO_GROUP("40003","用户不属于此群组"),

    /**
     * 群组不存在
     */
    GROUP_DONT_EXIST("40004","群组不存在"),

    /**
     * 群组解散，被删除
     */
    GROUP_HAS_BEEN_DELETED("40005","群组已解散"),

    /**
     * 普通用户无法删除群组成员
     */
    NORMAL_MEMBER_DELETE_NOT_PERMITTED("40005","普通用户无权删除群组成员"),

    /**
     * 删除群成员失败
     */
    DELETE_MEMBER_FAILED("40006","删除群成员失败,"),

    /**
     * 普通用户无法添加管理员
     */
    ADD_ADMIN_NOT_PERMITTED("40007","无权添加管理员"),

    /**
     * 普通用户无权移除管理员
     */
    DELETE_ADMIN_NOT_PERMITTED("40008","无权删除管理员"),

    /**
     * 非群主无法转让群
     */
    TRANS_NOT_PERMITTED("40009","无权转让群组"),

    /**
     * 非群主无法解散群
     */
    DISBAND_NOT_PERMITTED("40010","无权解散群"),

    /**
     * 群公告不存在
     */
    ANNOUNCE_NOT_EXIST("40011","群公告不存在"),

    /**
     * 进群链接失效
     */
    JOIN_GROUP_LINK_INVALID("40012","进群链接不合法或已失效"),

    /**
     * 用户已经在群里了
     */
    USER_HAS_IN_GROUP("40013","用户已经存在此群组,"),

    /**
     * 管理员数量超限
     */
    ADMIN_COUNT_EXCEED("40014","管理员数量超过限制"),

    /**
     * 群主或管理员不允许自己将自己移出群聊
     */
    REMOVER_SELF_NOT_COMMITTED("40015","无法将自己移除群聊"),

    /**
     * 创建群聊，群里的人查询不到
     */
    USER_SELECT_NOT_EXIST("40016","添加群组时，选中的用户不存在,"),

    /**
     * 群成员数量超限
     */
    GROUP_MEMBER_COUNT_EXCEED_LIMIT("40017","群成员数量超过限制,"),

    /**
     * 普通用户没有权限修改群组信息或配置
     */
    UPDATE_INFO_NOT_PERMITTED("40018","无权修改群组信息或配置"),

    /**
     * 同步的数量超限
     */
    SYNC_COUNT_EXCEED_LIMIT("40019","同步的数量超过限制,"),

    /**
     * 没有权限拉人进去
     */
    ADD_MEMBER_NOT_PERMITTED("40020","无权邀请人进群"),

    /**
     * 群聊无权生成分享链接
     */
    SHARE_NOT_PERMITTED("40021","不允许生成分享链接"),

    /**
     * user_info表中的用户信息不存在或已被删除
     */
    USER_INFO_INVALID("40022","无合法的用户，用户信息不存在或已被删除"),

    /**
     * 群主不允许退出群聊，只允许解散群聊
     */
    OWNER_QUIT_GROUP_NOT_PERMITTED("40023","群主无法退出群聊"),

    /***
     * 人数超出群聊最大限制
     */
    MAX_COUNT_EXCEED_LIMIT("40024","群聊最大人数超出限制"),

    /**
     * 至少3人成群
     */
    MEMBER_SIZE_CREATE_LESS_THAN_THREE("40025","建群时,群有效成员小于3"),

    /**
     * 查group_info时没查到
     */
    GROUP_INFO_NOT_EXIST("40026","未获取到群组信息,群组不存在或已被删除"),

    /**
     * 拉人进群，群成员列表为空
     */
    CREATE_GROUP_MEMBER_SIZE_ZERO("40027","创建群时，未传userIds"),

    /**
     * 不允许自己转让给自己
     */
    TRANSFER_TO_SELF_NOT_PERMITTED("40028","无法转让给自己"),

    /**
     * 传过来的用户列表，没有一个是群成员
     */
    USERS_NO_MEMBER_OF_GROUP("40029","用户列表，没有一个是群成员"),

    /***
     * 管理员无法删除其他管理员或群主
     */
    ADMIN_DELETE_ADMIN_OR_OWNER_NOT_PERMITTED("40030","管理员无法删除其他管理员或群主"),

    CREATE_GROUP_CREATOR_AND_MEMBER_NO_VALID("40031","创建群聊时，发起人和群成员的用户信息不存在或已被删除"),

    MEMBER_ADD_TO_GROUP_NO_VALID("40032","加入群聊的用户中无合法用户"),

    CREATOR_OF_GROUP_MEMBER_INFO_NOT_VALID("40033","发起群聊的用户信息不存在或已被删除"),

    JOIN_GROUP_USER_INVALID("40034","通过链接加群的人，用户信息不存在或已被删除"),

    SHARE_LINK_USER_INVALID("40035","分享链接的用户信息不存在或已被删除"),

    /**
     * 一个群群主只有1个，群里已有群主的情况下，新增群成员数据不能有type=3(群主)
     */
    MEMBER_SYNC_OWNER_HAS_EXIST("40036","同步群成员时，群里已有群主，请检查新增的用户类型是否正确")


    ;


    /**
     * 状态码
     */
    private String code;

    /**
     * 响应信息
     */
    private String message;

    GroupInfoRespCode(String code, String message) {
        this.code = code;
        this.message = message;
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
}
