package com.prog3.demo.dto;

import java.util.List;

public class DijkstraResultadoDTO {
    private List<String> recorrido;
    private double distanciaTotal;

    public DijkstraResultadoDTO(List<String> recorrido, double distanciaTotal) {
        this.recorrido = recorrido;
        this.distanciaTotal = distanciaTotal;
    }

    public List<String> getRecorrido() { return recorrido; }
    public double getDistanciaTotal() { return distanciaTotal; }
}
