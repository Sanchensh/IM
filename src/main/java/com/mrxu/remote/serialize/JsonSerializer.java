package com.mrxu.remote.serialize;

import com.alibaba.fastjson.JSON;
import com.mrxu.common.exception.DeserializationException;
import com.mrxu.common.exception.SerializationException;

/**
 * @Description: json序列化
 * @author: ztowh
 * @Date: 2018/11/22 18:24
 */
public class JsonSerializer implements Serializer {

    public static final byte Json = 2;

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        return JSON.toJSONBytes(o);
    }

    @Override
    public <T> T deserialize(byte[] bytes, String classOfT) throws DeserializationException {
        try {
            Class clazz = Class.forName(classOfT);
            return JSON.parseObject(bytes, clazz);
        } catch (ClassNotFoundException e) {
            throw new DeserializationException("IOException occurred when json serializer decode!", e);
        }
    }
}
