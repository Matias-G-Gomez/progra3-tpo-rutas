package com.prog3.demo.algorithm;

import com.prog3.demo.model.Lugar;
import com.prog3.demo.service.Vecino;
import java.util.*;

public class DFS {

    /**
     * DFS recursivo con control de visitados
     */
    public static List<Lugar> dfs(
            String origenId,
            Map<String, List<Vecino>> grafo,
            Map<String, Lugar> todos
    ) {
        List<Lugar> resultado = new ArrayList<>();
        Set<String> visitados = new HashSet<>();
        dfsRec(origenId, grafo, todos, visitados, resultado);
        return resultado;
    }

    private static void dfsRec(
            String actual,
            Map<String, List<Vecino>> grafo,
            Map<String, Lugar> todos,
            Set<String> visitados,
            List<Lugar> resultado
    ) {
        if (visitados.contains(actual)) return; // evita ciclos
        visitados.add(actual);

        Lugar lugar = todos.get(actual);
        if (lugar != null) resultado.add(lugar);

        // visitar todos los vecinos no visitados
        for (Vecino v : grafo.getOrDefault(actual, List.of())) {
            if (!visitados.contains(v.destinoId())) {
                dfsRec(v.destinoId(), grafo, todos, visitados, resultado);
            }
        }
    }
}
