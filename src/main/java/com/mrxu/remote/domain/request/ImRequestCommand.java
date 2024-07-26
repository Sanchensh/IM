package com.mrxu.remote.domain.request;

import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.Command;
import com.mrxu.common.exception.DeserializationException;
import com.mrxu.common.exception.SerializationException;
import com.mrxu.common.utils.SnowflakeIDGenUtils;
import com.mrxu.remote.serialize.SerializerManager;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2018/11/22 15:39
 */
public class ImRequestCommand extends AbstractRequestCommand {

    private Command requestObject;

    public ImRequestCommand() {
        super();
    }

    public ImRequestCommand(byte type) {
        super(type);
    }

    public ImRequestCommand(byte channel, Command request) {
        this.setId(SnowflakeIDGenUtils.getId());
        this.requestObject = request;
        this.setChannel(channel);
        this.setArriveTime(System.currentTimeMillis());
        this.setCmdCode(request.getCmdCode());
    }

    public ImRequestCommand(byte channel, byte type, ImCommandCode cmdCode) {
        super(channel, type, cmdCode);
    }

    @Override
    public void serialize() throws SerializationException {
        if (this.requestObject != null) {
            try {
                this.setBody(SerializerManager.getSerializer(this.getSerializer()).serialize(this.requestObject));
            } catch (SerializationException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deserialize() throws DeserializationException {
        if (this.requestObject == null) {
            try {
                if (this.getBody() != null) {
                    this.setRequestObject(SerializerManager.getSerializer(this.getSerializer()).deserialize(this.getBody(), ImCommandCode.classOf(this.getCmdCode().value())));
                }
            } catch (DeserializationException e) {
                e.printStackTrace();
            }
        }
    }


    public Command getRequestObject() {
        return requestObject;
    }

    public void setRequestObject(Command requestObject) {
        if (requestObject == null) {
            return;
        }
        this.requestObject = requestObject;
        this.setCmdCode(requestObject.getCmdCode());
    }
}
