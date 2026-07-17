package com.prog3.demo.dto;

import java.util.List;

public class BacktrackingResultadoDTO {
    private List<List<String>> combinaciones;

    public BacktrackingResultadoDTO(List<List<String>> combinaciones) {
        this.combinaciones = combinaciones;
    }

    public List<List<String>> getCombinaciones() {
        return combinaciones;
    }

    public void setCombinaciones(List<List<String>> combinaciones) {
        this.combinaciones = combinaciones;
    }
}
