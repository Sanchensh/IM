package com.mrxu.remote.domain.response;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.ImCommandType;
import com.mrxu.common.ResponseStatus;
import com.mrxu.common.domain.biz.Command;
import com.mrxu.common.exception.DeserializationException;
import com.mrxu.common.exception.SerializationException;
import com.mrxu.remote.domain.ImCommand;
import com.mrxu.remote.serialize.SerializerManager;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2018/11/22 15:40
 */
public class ImResponseCommand extends ImCommand {

    private ResponseStatus responseStatus;
    private long responseTimeMillis;
//    private InetSocketAddress responseHost;
//    private Throwable cause;

    private Command responseObject;
    private String errorMsg;

    public ImResponseCommand(byte type) {
        super(type);
    }

    public ImResponseCommand() {
        super(ImCommandType.RESPONSE);
    }


    public ImResponseCommand(ImCommandCode code) {
        super(ImCommandType.RESPONSE, code);
    }

    public ImResponseCommand(Command response) {
        super(ImCommandType.RESPONSE);
        this.responseObject = response;
        this.setCmdCode(responseObject.getCmdCode());
    }

    public ImResponseCommand(long id, Command response) {
        super(ImCommandType.RESPONSE, id);
        this.responseObject = response;
        this.setCmdCode(responseObject.getCmdCode());
    }

    public ImResponseCommand(byte type, ImCommandCode cmdCode) {
        super(type, cmdCode);
    }

    public ImResponseCommand(byte version, byte type, ImCommandCode cmdCode) {
        super(version, type, cmdCode);
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public long getResponseTimeMillis() {
        return responseTimeMillis;
    }

    public void setResponseTimeMillis(long responseTimeMillis) {
        this.responseTimeMillis = responseTimeMillis;
    }

//    public InetSocketAddress getResponseHost() {
//        return responseHost;
//    }
//
//    public void setResponseHost(InetSocketAddress responseHost) {
//        this.responseHost = responseHost;
//    }
//
//    public Throwable getCause() {
//        return cause;
//    }
//
//    public void setCause(Throwable cause) {
//        this.cause = cause;
//    }

    public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(Command responseObject) {
        if (responseObject == null) {
            return;
        }
        this.responseObject = responseObject;
        this.setCmdCode(responseObject.getCmdCode());
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }


    @Override
    public void serialize() throws SerializationException {
        if (this.getResponseObject() != null) {
            try {
                this.setBody(SerializerManager.getSerializer(this.getSerializer()).serialize(this.getResponseObject()));
            } catch (SerializationException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deserialize() throws DeserializationException {
        if (this.getResponseObject() == null) {
            if (this.getBody() != null) {
                try {
                    this.setResponseObject(SerializerManager.getSerializer(this.getSerializer()).deserialize(this.getBody(), ImCommandCode.classOf(this.getCmdCode().value())));
                } catch (DeserializationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
