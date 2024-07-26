package com.mrxu.remote.serialize;

public class SerializerManager {

    private static Serializer[] serializers = new Serializer[5];

    private static Serializer json = new JsonSerializer();

    static {
        addSerializer(JsonSerializer.Json, json);
    }

    public static Serializer getSerializer(int idx) {
        return serializers[idx] != null ? serializers[idx] : json;
    }

    public static void addSerializer(int idx, Serializer serializer) {
        if (serializers.length <= idx) {
            Serializer[] newSerializers = new Serializer[idx + 5];
            System.arraycopy(serializers, 0, newSerializers, 0, serializers.length);
            serializers = newSerializers;
        }
        serializers[idx] = serializer;
    }
}
