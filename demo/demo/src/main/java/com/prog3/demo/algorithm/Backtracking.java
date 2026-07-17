package com.prog3.demo.algorithm;

import com.prog3.demo.model.Lugar;
import com.prog3.demo.model.Ruta;

import java.util.*;

public class Backtracking {

    // Genera rutas posibles sin superar tiempoDisponible
    public static List<Ruta> generarItinerarios(List<Lugar> lugares, int maxLugares, int tiempoDisponible) {
        List<Ruta> resultado = new ArrayList<>();
        backtrack(new ArrayList<>(), new HashSet<>(), lugares, maxLugares, tiempoDisponible, resultado);
        return resultado;
    }

    private static void backtrack(
            List<Lugar> actual,
            Set<String> visitados,
            List<Lugar> todos,
            int maxLugares,
            int tiempoDisponible,
            List<Ruta> resultado
    ) {
        int tiempoActual = actual.stream().mapToInt(Lugar::getTiempoRecomendadoMin).sum();

        // 🔹 Poda: si ya excede el tiempo disponible, detener esta rama
        if (tiempoActual > tiempoDisponible) {
            return;
        }

        // 🔹 Caso base: si llegamos al tamaño máximo permitido
        if (actual.size() == maxLugares) {
            double totalDistancia = actual.size() * 2.0; // (opcional)
            resultado.add(new Ruta(new ArrayList<>(actual), totalDistancia, 0, tiempoActual));
            return;
        }

        // 🔹 Exploramos nuevas combinaciones
        for (Lugar l : todos) {
            if (!visitados.contains(l.getId())) {
                visitados.add(l.getId());
                actual.add(l);

                backtrack(actual, visitados, todos, maxLugares, tiempoDisponible, resultado);

                actual.remove(actual.size() - 1);
                visitados.remove(l.getId());
            }
        }
    }
}
