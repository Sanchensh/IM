package com.mrxu.common;

import com.dianping.cat.Cat;

import java.util.HashMap;
import java.util.Map;

public class MQCatContext implements Cat.Context {
    private Map<String, String> properties = new HashMap();

    public MQCatContext() {
    }

    public void addProperty(String key, String value) {
        this.properties.put(key, value);
    }

    public String getProperty(String key) {
        return (String)this.properties.get(key);
    }
}
