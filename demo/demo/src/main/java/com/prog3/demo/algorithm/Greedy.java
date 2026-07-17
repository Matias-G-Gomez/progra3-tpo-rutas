package com.prog3.demo.algorithm;

import com.prog3.demo.model.Lugar;
import com.prog3.demo.model.Ruta;
import com.prog3.demo.service.Vecino;
import java.util.*;

public class Greedy {

    /**
     * Genera una ruta aproximada visitando siempre el vecino más cercano
     */
    public static Ruta calcularRutaGreedy(String origenId,
                                          List<String> lugaresDeseados,
                                          Map<String, List<Vecino>> grafo,
                                          Map<String, Lugar> todos) {

        if (!grafo.containsKey(origenId)) {
            throw new IllegalArgumentException("Origen inexistente: " + origenId);
        }

        Set<String> visitados = new HashSet<>();
        List<Lugar> ruta = new ArrayList<>();
        double distanciaTotal = 0;

        String actual = origenId;
        visitados.add(actual);
        ruta.add(todos.get(actual));

        // Mientras queden lugares deseados sin visitar
        while (visitados.size() <= lugaresDeseados.size()) {
            Vecino masCercano = null;

            // Buscar el vecino más cercano entre los destinos aún no visitados
            for (Vecino v : grafo.getOrDefault(actual, List.of())) {
                if (!visitados.contains(v.destinoId()) && lugaresDeseados.contains(v.destinoId())) {
                    if (masCercano == null || v.distanciaKm() < masCercano.distanciaKm()) {
                        masCercano = v;
                    }
                }
            }

            // Si no quedan vecinos válidos, terminamos
            if (masCercano == null) break;

            distanciaTotal += masCercano.distanciaKm();
            actual = masCercano.destinoId();
            visitados.add(actual);
            ruta.add(todos.get(actual));
        }

        return new Ruta(ruta, distanciaTotal, 0, 0);
    }
}
