package com.mrxu.remote.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.mrxu.common.ImCommandCode;
import com.mrxu.common.exception.DeserializationException;
import com.mrxu.common.exception.SerializationException;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2018/11/22 14:58
 */
public abstract class ImCommand implements RemotingCommand {

    // 请求类型: request/response
    private byte type;
    private ImCommandCode cmdCode;
    private byte serializer;
    @JSONField(serializeUsing = ToStringSerializer.class)
    private long id;
    @JSONField(serialize = false)
    private int bodyLength;
    @JSONField(serialize = false)
    private byte[] body;
    private String biz;
    private byte channel;

    public ImCommand() {
    }

    public ImCommand(byte type) {
        this.type = type;
    }

    public ImCommand(byte type, ImCommandCode cmdCode) {
        this(type);
        this.cmdCode = cmdCode;
    }


    public ImCommand(byte type, long id) {
        this(type);
        this.id = id;
    }

    public ImCommand(byte channel, byte type, ImCommandCode cmdCode) {
        this(type, cmdCode);
        this.channel = channel;
    }

    @Override
    public byte getProtocolCode() {
        return ProtocolByte.IM_PROTOCOL_CODE;
    }

    @Override
    public ImCommandCode getCmdCode() {
        return cmdCode;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public byte getSerializer() {
        return serializer;
    }

    @Override
    public String getBiz() {
        return biz;
    }

    public void setBiz(String biz) {
        this.biz = biz;
    }

    @Override
    public byte getChannel() {
        return channel;
    }

    public void setChannel(byte channel) {
        this.channel = channel;
    }

    @Override
    public void serialize() throws SerializationException {

    }

    @Override
    public void deserialize() throws DeserializationException {

    }

    @Override
    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }


    public void setSerializer(byte serializer) {
        this.serializer = serializer;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getBodyLength() {
        return bodyLength;
    }

    public void setBodyLength(int bodyLength) {
        this.bodyLength = bodyLength;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        if (body != null) {
            this.body = body;
            this.bodyLength = body.length;
        }
    }

    public void setCmdCode(ImCommandCode cmdCode) {
        if (cmdCode != null) {
            this.cmdCode = cmdCode;
        }
    }
}
