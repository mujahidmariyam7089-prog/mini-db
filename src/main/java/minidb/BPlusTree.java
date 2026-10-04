package com.mujah.minidb;

import java.util.Set;
import java.util.TreeMap;

public class BPlusTree {
    private final TreeMap<String, Long> map = new TreeMap<>();

    public void put(String key, Long offset) {
        map.put(key, offset);
    }

    public Long get(String key) {
        return map.get(key);
    }

    public void remove(String key) {
        map.remove(key);
    }
    
    public Set<String> keys() {
        return map.keySet();
    }
}
