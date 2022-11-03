package br.com.pensarcomodev.util;

import java.util.HashMap;
import java.util.Map;

public class ParamMap {

    private Map<String, Object> paramMap = new HashMap<>();

    public ParamMap kv(String key, Object value) {
        paramMap.put(key, value);
        return this;
    }

    public Map<String, Object> getMap() {
        return paramMap;
    }
}
