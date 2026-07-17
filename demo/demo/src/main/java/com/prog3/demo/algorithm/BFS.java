package com.prog3.demo.algorithm;

import com.prog3.demo.model.Lugar;
import com.prog3.demo.service.Vecino;
import com.prog3.demo.dto.LugarDistanciaDTO;

import java.util.*;

public class BFS {

    // BFS (Breadth-First Search) recorre un grafo “por capas” desde un nodo origen
    // Visita primero todos los vecinos a 1 salto, luego los de 2 saltos, etc
    // Sirva para --> ¿Qué lugares puedo visitar sin pasarme de X km?

    // En este caso:
    // 1) Listar lugares alcanzables desde un origen con un radio de distancia (acumulado en km)
    // 2) (Opcional) listar los alcanzables por máximo de saltos.


    /**
     * BFS por radio (distancia acumulada <= radioKm)
     */
    public static List<LugarDistanciaDTO> bfs(
            String origenId,
            double radioKm,
            Map<String, List<Vecino>> grafo,
            Map<String, Lugar> todosLosLugares
    ) {
        if (!grafo.containsKey(origenId)) {
            throw new IllegalArgumentException("Origen inexistente: " + origenId);
        }

        Queue<String> cola = new LinkedList<>();
        Map<String, Double> distanciaAcumulada = new HashMap<>();
        List<LugarDistanciaDTO> resultado = new ArrayList<>();

        cola.add(origenId);
        distanciaAcumulada.put(origenId, 0.0);

        while (!cola.isEmpty()) {
            String actual = cola.poll();
            double dActual = distanciaAcumulada.get(actual);

            Lugar l = todosLosLugares.get(actual);
            if (l != null)
                resultado.add(new LugarDistanciaDTO(l.getId(), l.getNombre(), dActual));

            for (Vecino v : grafo.getOrDefault(actual, List.of())) {
                double nuevo = dActual + v.distanciaKm();

                if (nuevo <= radioKm && !distanciaAcumulada.containsKey(v.destinoId())) {
                    distanciaAcumulada.put(v.destinoId(), nuevo);
                    cola.add(v.destinoId());
                }
            }
        }

        return resultado;
    }


    public static List<LugarDistanciaDTO> bfsPorSaltos(
            String origenId,
            int maxSaltos,
            Map<String, List<Vecino>> grafo,
            Map<String, Lugar> todos
    ) {
        if (!grafo.containsKey(origenId)) {
            throw new IllegalArgumentException("Origen inexistente: " + origenId);
        }

        Queue<String> cola = new LinkedList<>();
        Queue<Integer> saltos = new LinkedList<>();
        Set<String> visitados = new HashSet<>();
        List<LugarDistanciaDTO> resultado = new ArrayList<>();

        cola.add(origenId);
        saltos.add(0);
        visitados.add(origenId);

        resultado.add(new LugarDistanciaDTO(origenId, todos.get(origenId).getNombre(), 0));

        while (!cola.isEmpty()) {
            String actual = cola.poll();
            int sActual = saltos.poll();

            if (sActual == maxSaltos)
                continue;

            for (Vecino v : grafo.getOrDefault(actual, List.of())) {
                if (!visitados.contains(v.destinoId())) {
                    visitados.add(v.destinoId());
                    cola.add(v.destinoId());
                    saltos.add(sActual + 1);

                    resultado.add(new LugarDistanciaDTO(
                            v.destinoId(),
                            todos.get(v.destinoId()).getNombre(),
                            sActual + 1   // aquí medimos saltos
                    ));
                }
            }
        }

        return resultado;
    }



}