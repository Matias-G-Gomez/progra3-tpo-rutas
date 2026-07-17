package com.prog3.demo.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;


@Node("Lugar")
public class Lugar {
    @Id 
    private String id;
    private String nombre;
    private String tipo; // museo, parque, restaurante...
    private double popularidad;
    private double precioEntrada;
    private int tiempoRecomendadoMin; // minutos

    @Relationship(type = "CONECTA", direction = Relationship.Direction.OUTGOING)
    private List<Conexion> conexiones;

    public Lugar() {}


    public Lugar(String id, String nombre) {
        this.id = id; 
        this.nombre = nombre;
        /*this.tipo=tipo;
        this.popularidad=popularidad;
        this.precioEntrada=precioEntrada;
        this.tiempoRecomendadoMin=tiempoRecomendadoMin;*/
    }

    // getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getPopularidad() { return popularidad; }
    public void setPopularidad(double popularidad) { this.popularidad = popularidad; }

    public double getPrecioEntrada() { return precioEntrada; }
    public void setPrecioEntrada(double precioEntrada) { this.precioEntrada = precioEntrada; }

    public int getTiempoRecomendadoMin() { return tiempoRecomendadoMin; }
    public void setTiempoRecomendadoMin(int tiempoRecomendadoMin) { this.tiempoRecomendadoMin = tiempoRecomendadoMin; }

    public List<Conexion> getConexiones() { return conexiones; }
    public void setConexiones(List<Conexion> conexiones) { this.conexiones = conexiones; }

}
