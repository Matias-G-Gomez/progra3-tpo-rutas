package com.prog3.demo.dto;

import java.util.List;

public class GreedyResultadoDTO {
    private List<String> recorrido;
    private double distanciaTotal;

    public GreedyResultadoDTO(List<String> recorrido, double distanciaTotal) {
        this.recorrido = recorrido;
        this.distanciaTotal = distanciaTotal;
    }

    public List<String> getRecorrido() { return recorrido; }
    public double getDistanciaTotal() { return distanciaTotal; }
}
