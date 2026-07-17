package com.prog3.demo.algorithm;

import com.prog3.demo.model.Lugar;
import com.prog3.demo.service.Vecino;

import java.util.*;

public class Prim {

    // Este código:
    // - selecciona siempre la conexión más barata disponible
    // - evita ciclos automáticamente
    // - genera una red mínima

    public static List<String> prim(
            Map<String, List<Vecino>> grafo,
            Map<String, Lugar> todos
    ) {
        if (grafo.isEmpty()) return List.of();

        String start = grafo.keySet().iterator().next(); // primer nodo

        Set<String> visitados = new HashSet<>();
        PriorityQueue<Arista> pq = new PriorityQueue<>();
        List<String> resultado = new ArrayList<>();

        visitados.add(start);

        // meter vecinos iniciales
        for (Vecino v : grafo.get(start)) {
            pq.offer(new Arista(start, v.destinoId(), v.distanciaKm()));
        }

        // mientras haya aristas
        while (!pq.isEmpty()) {
            Arista a = pq.poll();

            if (visitados.contains(a.destino)) continue;

            visitados.add(a.destino);

            resultado.add(
                    a.origen + " --(" + a.peso + "km)--> " + a.destino
            );

            for (Vecino v : grafo.getOrDefault(a.destino, List.of())) {
                if (!visitados.contains(v.destinoId())) {
                    pq.offer(new Arista(a.destino, v.destinoId(), v.distanciaKm()));
                }
            }
        }

        return resultado;
    }

    private record Arista(String origen, String destino, double peso)
            implements Comparable<Arista> {

        @Override
        public int compareTo(Arista o) {
            return Double.compare(this.peso, o.peso);
        }
    }
}
