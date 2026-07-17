package com.prog3.demo.dto;

import com.prog3.demo.model.Lugar;

public class LugarSimpleDTO {
    private String id;
    private String nombre;
    private String tipo;

    public LugarSimpleDTO(String id, String nombre, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public static LugarSimpleDTO from(Lugar l) {
        return new LugarSimpleDTO(l.getId(), l.getNombre(), l.getTipo());
    }

    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
}
