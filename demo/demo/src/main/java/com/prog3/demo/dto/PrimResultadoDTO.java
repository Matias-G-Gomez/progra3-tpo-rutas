package com.prog3.demo.dto;

public class PrimResultadoDTO {
    private String conexion;
    private double distanciaKm;

    public PrimResultadoDTO(String conexion, double distanciaKm) {
        this.conexion = conexion;
        this.distanciaKm = distanciaKm;
    }

    public String getConexion() { return conexion; }
    public double getDistanciaKm() { return distanciaKm; }
}
