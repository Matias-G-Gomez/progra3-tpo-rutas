package com.prog3.demo.model;

import java.util.List;

public class Usuario {

    private String id;
    private String nombre;
    private List<String> preferencias; // tipos de lugares
    private int tiempoDisponibleMin;   // tiempo total disponible (en minutos)
    private double presupuesto;

    public Usuario() {
    }

    public Usuario(String id, String nombre, List<String> preferencias, int tiempoDisponibleMin, double presupuesto) {
        this.id = id;
        this.nombre = nombre;
        this.preferencias = preferencias;
        this.tiempoDisponibleMin = tiempoDisponibleMin;
        this.presupuesto = presupuesto;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<String> getPreferencias() {
        return preferencias;
    }

    public int getTiempoDisponibleMin() {
        return tiempoDisponibleMin;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPreferencias(List<String> preferencias) {
        this.preferencias = preferencias;
    }

    public void setTiempoDisponibleMin(int tiempoDisponibleMin) {
        this.tiempoDisponibleMin = tiempoDisponibleMin;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }
}
