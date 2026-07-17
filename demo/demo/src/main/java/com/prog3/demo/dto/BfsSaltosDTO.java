package com.prog3.demo.dto;

public class BfsSaltosDTO {
    private String id;
    private String nombre;
    private int saltos;

    public BfsSaltosDTO(String id, String nombre, int saltos) {
        this.id = id;
        this.nombre = nombre;
        this.saltos = saltos;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public int getSaltos() { return saltos; }
}
