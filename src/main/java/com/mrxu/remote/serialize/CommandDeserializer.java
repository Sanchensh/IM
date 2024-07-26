package com.mrxu.remote.serialize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.mrxu.common.ImCommandCode;
import com.mrxu.common.domain.biz.Command;
import com.mrxu.remote.domain.request.ImRequestCommand;
import com.mrxu.remote.domain.response.ImResponseCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

/**
 * @Description: 用来根据上下文之前转换的cmdcode转换Command
 * @author: ztowh
 * @Date: 2019-01-02 11:16
 */
public class CommandDeserializer implements ObjectDeserializer {

    private Logger logger = LoggerFactory.getLogger(CommandDeserializer.class);

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        Command command = null;
        try {
            if (parser.getContext().object instanceof ImRequestCommand) {
                ImRequestCommand requestCommand = (ImRequestCommand) parser.getContext().object;
                ImCommandCode cmdCode = requestCommand.getCmdCode();
                Class<?> clazz = Class.forName(cmdCode.clazz());
                JSONObject jsonObject = parser.parseObject();
                command = (Command) JSONObject.toJavaObject(jsonObject, clazz);
            } else if (parser.getContext().object instanceof ImResponseCommand) {
                ImResponseCommand responseCommand = (ImResponseCommand) parser.getContext().object;
                ImCommandCode cmdCode = responseCommand.getCmdCode();
                Class<?> clazz = Class.forName(cmdCode.clazz());
                JSONObject jsonObject = parser.parseObject();
                command = (Command) JSONObject.toJavaObject(jsonObject, clazz);
            }
        } catch (Exception e) {
            logger.error("转换command出现异常, parseContent: " + JSON.toJSON(parser.getContext()), e);
        }
        return (T) command;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
