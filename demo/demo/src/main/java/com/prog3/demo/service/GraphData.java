package com.prog3.demo.service;

import com.prog3.demo.model.Lugar;

import java.util.List;
import java.util.Map;

public class GraphData {
    public Map<String, List<Vecino>> grafo;
    public Map<String, Lugar> todos;

    public GraphData(Map<String, List<Vecino>> grafo, Map<String, Lugar> todos) {
        this.grafo = grafo;
        this.todos = todos;
    }
}
