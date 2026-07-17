package com.prog3.demo.algorithm;

import com.prog3.demo.model.Conexion;
import com.prog3.demo.model.Lugar;

import java.util.*;

public class Kruskal {

    /**
     * Genera un Árbol de Expansión Mínima usando Kruskal
     */
    public static List<String> generarMST(List<Lugar> lugares) {

        // 0) Forzar carga de relaciones (por si SDN usa lazy)
        lugares.forEach(l -> {
            if (l.getConexiones() != null) l.getConexiones().size();
        });

        // 1) Arista simple con origen, destino y peso (sin tocar Conexion)
        class Edge {
            final String origenId;
            final String destinoId;
            final double distancia;
            Edge(String o, String d, double w) { this.origenId = o; this.destinoId = d; this.distancia = w; }
        }

        // 2) Construir todas las aristas tomando el LUGAR como origen
        List<Edge> edges = new ArrayList<>();
        for (Lugar l : lugares) {
            if (l.getConexiones() == null) continue;
            l.getConexiones().forEach(c ->
                    edges.add(new Edge(
                            l.getId(),                      // origen = dueño de la lista
                            c.getDestino().getId(),         // destino viene en la Conexion
                            c.getDistanciaKm()              // peso
                    ))
            );
        }

        // 3) Ordenar por peso ascendente
        edges.sort(Comparator.comparingDouble(e -> e.distancia));

        // 4) Union-Find con todos los nodos
        UnionFind uf = new UnionFind();
        lugares.forEach(l -> uf.add(l.getId()));

        // 5) Seleccionar aristas que no formen ciclo
        List<String> resultado = new ArrayList<>();
        for (Edge e : edges) {
            if (!uf.connected(e.origenId, e.destinoId)) {
                uf.union(e.origenId, e.destinoId);
                resultado.add(e.origenId + " -- " + e.destinoId + " (" + e.distancia + " km)");
            }
        }

        return resultado;
    }


}
