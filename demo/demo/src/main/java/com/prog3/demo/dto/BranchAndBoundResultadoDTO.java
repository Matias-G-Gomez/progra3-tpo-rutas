package com.prog3.demo.dto;

import java.util.List;

public class BranchAndBoundResultadoDTO {
    private List<String> lugaresSeleccionados;
    private double popularidadTotal;
    private double tiempoTotalMin;
    private double costoTotal;

    public BranchAndBoundResultadoDTO() {}

    public BranchAndBoundResultadoDTO(List<String> lugaresSeleccionados, double popularidadTotal, double tiempoTotalMin, double costoTotal) {
        this.lugaresSeleccionados = lugaresSeleccionados;
        this.popularidadTotal = popularidadTotal;
        this.tiempoTotalMin = tiempoTotalMin;
        this.costoTotal = costoTotal;
    }

    public List<String> getLugaresSeleccionados() { return lugaresSeleccionados; }
    public void setLugaresSeleccionados(List<String> lugaresSeleccionados) { this.lugaresSeleccionados = lugaresSeleccionados; }

    public double getPopularidadTotal() { return popularidadTotal; }
    public void setPopularidadTotal(double popularidadTotal) { this.popularidadTotal = popularidadTotal; }

    public double getTiempoTotalMin() { return tiempoTotalMin; }
    public void setTiempoTotalMin(double tiempoTotalMin) { this.tiempoTotalMin = tiempoTotalMin; }

    public double getCostoTotal() { return costoTotal; }
    public void setCostoTotal(double costoTotal) { this.costoTotal = costoTotal; }
}
