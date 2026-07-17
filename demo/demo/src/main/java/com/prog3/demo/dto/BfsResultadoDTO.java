package com.prog3.demo.dto;

public class BfsResultadoDTO {
    private String id;
    private String nombre;
    private double distanciaAcumulada;

    public BfsResultadoDTO(String id, String nombre, double distanciaAcumulada) {
        this.id = id;
        this.nombre = nombre;
        this.distanciaAcumulada = distanciaAcumulada;
    }

    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public double getDistanciaAcumulada() { return distanciaAcumulada; }
}
