package com.mrxu.remote.serialize;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.mrxu.common.ImCommandCode;

import java.lang.reflect.Type;

public class CommandCodeEnumDeser implements ObjectDeserializer {

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        short shortValue = parser.parseObject(short.class);
        return (T) ImCommandCode.valueOf(shortValue);
    }

    @Override
    public int getFastMatchToken() {
        return JSONToken.LITERAL_INT;
    }
}