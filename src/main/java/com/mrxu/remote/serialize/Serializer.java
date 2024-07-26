package com.mrxu.remote.serialize;

import com.mrxu.common.exception.DeserializationException;
import com.mrxu.common.exception.SerializationException;

public interface Serializer {
    /**
     * Encode object into bytes.
     *
     * @param obj target object
     * @return serialized result
     */
    byte[] serialize(final Object obj) throws SerializationException;

    /**
     * Decode bytes into Object.
     *
     * @param data     serialized data
     * @param classOfT class of original data
     */
    <T> T deserialize(final byte[] data, String classOfT) throws DeserializationException;
}
