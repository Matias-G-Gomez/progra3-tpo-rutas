package com.prog3.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
public class Conexion {

    @Id
    @GeneratedValue
    private Long id;

    private double distanciaKm;
    private double tiempoMin;
    private double costo;

    @TargetNode
    private Lugar destino;

    public Conexion() {}

    public Conexion(Lugar destino, double distanciaKm, double tiempoMin, double costo) {
        this.destino = destino;
        this.distanciaKm = distanciaKm;
        this.tiempoMin = tiempoMin;
        this.costo = costo;
    }

    // ✅ Getters y setters completos
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Lugar getDestino() { return destino; }
    public void setDestino(Lugar destino) { this.destino = destino; }

    public double getDistanciaKm() { return distanciaKm; }
    public void setDistanciaKm(double distanciaKm) { this.distanciaKm = distanciaKm; }

    public double getTiempoMin() { return tiempoMin; }
    public void setTiempoMin(double tiempoMin) { this.tiempoMin = tiempoMin; }

    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }
}
