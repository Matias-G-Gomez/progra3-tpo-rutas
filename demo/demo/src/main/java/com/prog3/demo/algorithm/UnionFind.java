package com.prog3.demo.algorithm;

import java.util.HashMap;
import java.util.Map;

public class UnionFind {

    private Map<String, String> parent = new HashMap<>();

    public void add(String x) {
        parent.putIfAbsent(x, x);
    }

    public String find(String x) {
        if (!parent.get(x).equals(x)) {
            parent.put(x, find(parent.get(x)));
        }
        return parent.get(x);
    }

    public void union(String a, String b) {
        parent.put(find(a), find(b));
    }

    public boolean connected(String a, String b) {
        return find(a).equals(find(b));
    }
}
