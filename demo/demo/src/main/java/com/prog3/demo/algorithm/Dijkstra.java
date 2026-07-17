package com.prog3.demo.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import com.prog3.demo.model.Lugar;
import com.prog3.demo.model.Ruta;

public class Dijkstra {

    /**
     * Calcula la ruta mínima entre origenId y destinoId en el grafo dado.
     * 
     * @param origenId       ID del nodo de origen
     * @param destinoId      ID del nodo de destino
     * @param grafo          Mapa: nodo -> lista de conexiones
     * @param todosLosLugares Mapa de todos los nodos con información de Lugar
     * @return Ruta con lista de lugares y distancia total
     * @throws IllegalArgumentException si origen o destino no existen en el grafo, 
     *         o si no hay camino entre ellos
     */
    public static Ruta calcularRuta(String origenId, String destinoId,
                                    Map<String, List<ConexionInterna>> grafo,
                                    Map<String, Lugar> todosLosLugares) {

        if (!grafo.containsKey(origenId) || !grafo.containsKey(destinoId)) {
            throw new IllegalArgumentException("Origen o destino no existe en el grafo");
        }

        Map<String, Double> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();
        PriorityQueue<NodoDistancia> pq = new PriorityQueue<>(Comparator.comparingDouble(nd -> nd.distancia));

        // Inicializar distancias
        for (String id : grafo.keySet()) {
            dist.put(id, Double.MAX_VALUE);
        }
        dist.put(origenId, 0.0);
        pq.add(new NodoDistancia(origenId, 0.0));

        while (!pq.isEmpty()) {
            NodoDistancia actual = pq.poll();

            if (actual.id.equals(destinoId)) break;

            for (ConexionInterna conn : grafo.getOrDefault(actual.id, Collections.emptyList())) {
                Double distActual = dist.get(actual.id);
                if (distActual == null) continue;

                double alt = distActual + conn.distancia;
                Double distDestino = dist.get(conn.destinoId);
                if (distDestino == null || alt < distDestino) {
                    dist.put(conn.destinoId, alt);
                    prev.put(conn.destinoId, actual.id);
                    pq.add(new NodoDistancia(conn.destinoId, alt));
                }
            }
        }

        double distanciaTotal = dist.getOrDefault(destinoId, Double.MAX_VALUE);
        if (distanciaTotal == Double.MAX_VALUE) {
            throw new IllegalArgumentException("No existe camino entre el origen y el destino");
        }

        // Reconstruir ruta
        List<Lugar> rutaLugares = new ArrayList<>();
        String u = destinoId;
        while (u != null) {
            Lugar lugar = todosLosLugares.getOrDefault(u, new Lugar(u, u.toString()));
            rutaLugares.add(lugar);
            u = prev.get(u);
        }
        Collections.reverse(rutaLugares);

        return new Ruta(rutaLugares, distanciaTotal, 0, 0);
    }

    // Helper interno
    private static class NodoDistancia {
        String id;
        double distancia;
        NodoDistancia(String id, double distancia) { this.id = id; this.distancia = distancia; }
    }

    // Clase para representar conexiones del grafo
    public static class ConexionInterna {
        String destinoId;
        double distancia;
        public ConexionInterna(String destinoId, double distancia) {
            this.destinoId = destinoId; 
            this.distancia = distancia; 
        }
        public String getDestinoId() {
        return destinoId;
        }

        public double getDistancia() {
            return distancia;
        }
    }
}