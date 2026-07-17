package com.prog3.demo.dto;

import java.util.List;

public class DynamicProgrammingResultadoDTO {
    private List<String> lugaresSeleccionados;
    private double popularidadTotal;
    private int tiempoTotal;

    public DynamicProgrammingResultadoDTO(List<String> lugaresSeleccionados, double popularidadTotal, int tiempoTotal) {
        this.lugaresSeleccionados = lugaresSeleccionados;
        this.popularidadTotal = popularidadTotal;
        this.tiempoTotal = tiempoTotal;
    }

    public List<String> getLugaresSeleccionados() {
        return lugaresSeleccionados;
    }

    public double getPopularidadTotal() {
        return popularidadTotal;
    }

    public int getTiempoTotal() {
        return tiempoTotal;
    }
}
