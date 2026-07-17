package com.prog3.demo.service;

import com.prog3.demo.algorithm.*;
import com.prog3.demo.dto.*;
import com.prog3.demo.model.Conexion;
import com.prog3.demo.model.Lugar;
import com.prog3.demo.model.Ruta;
import com.prog3.demo.model.Usuario;
import com.prog3.demo.repo.LugarRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PlanificadorRutasService {

    private final LugarRepository lugarRepository;

    public PlanificadorRutasService(LugarRepository lugarRepository) {
        this.lugarRepository = lugarRepository;
    }

    private GraphData buildGraph() {
        var lugares = lugarRepository.findAll();

        Map<String, List<Vecino>> grafo = new HashMap<>();
        Map<String, Lugar> todos = new HashMap<>();

        for (Lugar l : lugares) {
            todos.put(l.getId(), l);
            grafo.put(l.getId(), new ArrayList<>());

            if (l.getConexiones() != null) {
                l.getConexiones().forEach(c ->
                        grafo.get(l.getId()).add(new Vecino(c.getDestino().getId(), c.getDistanciaKm()))
                );
            }
        }

        return new GraphData(grafo, todos);
    }


    public List<Lugar> obtenerTodosLosLugares() {
        return lugarRepository.findAll();
    }



    /**
     * Calcula la ruta mínima entre dos lugares usando Dijkstra
     * Maneja errores si origen/destino no existen o no hay camino
     */
    public Ruta calcularRutaMinima(String origenId, String destinoId) {

        // 1️⃣ Obtener lugares desde Neo4j
        List<Lugar> lugares = lugarRepository.findAll();

        // ✅ Forzar carga de relaciones CONECTA
        lugares.forEach(l -> {
            if (l.getConexiones() != null)
                l.getConexiones().size();
        });

        System.out.println("===== LUGARES =====");
        System.out.println("Cantidad: " + lugares.size());

        // 2️⃣ Construir grafo para Dijkstra
        Map<String, List<Dijkstra.ConexionInterna>> grafo = new HashMap<>();
        Map<String, Lugar> todosLosLugares = new HashMap<>();

        for (Lugar l : lugares) {
            todosLosLugares.put(l.getId(), l);
            List<Dijkstra.ConexionInterna> conexiones = new ArrayList<>();

            if (l.getConexiones() != null) {
                for (Conexion c : l.getConexiones()) {
                    conexiones.add(new Dijkstra.ConexionInterna(
                            c.getDestino().getId(),
                            c.getDistanciaKm()
                    ));
                }
            }

            grafo.put(l.getId(), conexiones);
        }

        // ✅ Log de grafo para verificar que sí cargó conexiones
        grafo.forEach((id, lista) -> {
            System.out.print("Nodo " + id + " -> ");
            lista.forEach(c -> System.out.print(c.getDestinoId() + "(" + c.getDistancia() + ") "));
            System.out.println();
        });

        try {
            return Dijkstra.calcularRuta(origenId, destinoId, grafo, todosLosLugares);
        } catch (Exception e) {
            System.err.println("Error en Dijkstra: " + e.getMessage());
            return new Ruta(new ArrayList<>(), Double.MAX_VALUE, 0, 0);
        }
    }


    // BFS: lugares alcanzables desde un origen con un radio (km)
    public List<LugarDistanciaDTO> bfsLugaresAlcanzables(String origenId, double radioKm) {
        GraphData gd = buildGraph();
        return BFS.bfs(origenId, radioKm, gd.grafo, gd.todos);
    }


    public List<LugarDistanciaDTO> bfsPorSaltos(String origenId, int maxSaltos) {
        GraphData gd = buildGraph();
        return BFS.bfsPorSaltos(origenId, maxSaltos, gd.grafo, gd.todos);
    }


    // Prim / Kruskal: red mínima que conecte los lugares

    // Prim
    public List<String> generarRedMinimaPrim() {
        GraphData gd = buildGraph();
        return Prim.prim(gd.grafo, gd.todos);
    }

    // Kruskal
    public List<String> generarRedMinimaKruskal() {
        List<Lugar> lugares = lugarRepository.findAll();
        return Kruskal.generarMST(lugares);
    }


    // Greedy: heurística "vecino más cercano" para generar ruta rápida
    public Ruta generarRutaGreedy(String origenId, List<String> lugaresDeseados) {
        GraphData gd = buildGraph();

        List<String> pendientes = new ArrayList<>(lugaresDeseados);
        List<Lugar> recorrido = new ArrayList<>();

        String actual = origenId;
        double distanciaTotal = 0.0;

        // agregar origen
        recorrido.add(gd.todos.get(actual));
        pendientes.remove(actual);

        while (!pendientes.isEmpty()) {
            String mejor = null;
            double mejorDist = Double.MAX_VALUE;

            // buscar entre pendientes el más cercano
            for (String candidato : pendientes) {
                double d = distanciaEntre(gd.grafo, actual, candidato);
                if (d < mejorDist) {
                    mejorDist = d;
                    mejor = candidato;
                }
            }

            // avanzar
            if (mejor == null) break; // no hay más caminos

            distanciaTotal += mejorDist;
            actual = mejor;
            recorrido.add(gd.todos.get(actual));
            pendientes.remove(actual);
        }

        return new Ruta(recorrido, distanciaTotal, 0, recorrido.size());
    }

    // agregamos "distanciaEntre"
    // Esto asume que el grafo tiene caminos directos entre los lugares
    private double distanciaEntre(Map<String, List<Vecino>> grafo, String a, String b) {
        for (Vecino v : grafo.getOrDefault(a, List.of())) {
            if (v.destinoId().equals(b)) {
                return v.distanciaKm();
            }
        }
        return Double.MAX_VALUE; // si no hay conexión directa
    }


    // Ordenar: quicksort / mergesort wrapper

    // Quicksort
    public List<Lugar> ordenarLugares(List<Lugar> lista, String criterio, boolean ascendente) {
        QuickSort.quickSort(lista, criterio, ascendente);
        return lista;
    }

    // Mergesort Wrapper
    public List<Lugar> ordenarLugaresMerge(List<Lugar> lista, String criterio, boolean ascendente) {
        return MergeSort.mergeSort(lista, criterio, ascendente);
    }


    // DFS: búsqueda en profundidad (recorre el grafo completo desde un origen)
    public List<DfsResultadoDTO> dfsLugares(String origenId) {
        GraphData gd = buildGraph();
        List<Lugar> lugares = DFS.dfs(origenId, gd.grafo, gd.todos);
        return lugares.stream()
                .map(l -> new DfsResultadoDTO(l.getId(), l.getNombre(), l.getTipo()))
                .toList();
    }


    // Programación dinámica: optimizar itinerario under time constraint
    public DynamicProgrammingResultadoDTO optimizarItinerarioDP(Usuario usuario, List<String> lugaresDeseados) {
        List<Lugar> todos = lugarRepository.findAll();
        List<Lugar> seleccionados = todos.stream()
                .filter(l -> lugaresDeseados.contains(l.getId()))
                .toList();

        int tiempoDisponible = usuario.getTiempoDisponibleMin(); // tiempo máximo permitido
        List<Lugar> resultado = DynamicProgramming.optimizar(seleccionados, tiempoDisponible);

        double popularidadTotal = resultado.stream().mapToDouble(Lugar::getPopularidad).sum();
        int tiempoUsado = resultado.stream().mapToInt(Lugar::getTiempoRecomendadoMin).sum();
        List<String> nombres = resultado.stream().map(Lugar::getNombre).toList();

        return new DynamicProgrammingResultadoDTO(nombres, popularidadTotal, tiempoUsado);
    }


    // Backtracking: generar combinaciones válidas
    public BacktrackingResultadoDTO generarItinerariosBacktracking(Usuario usuario, List<String> lugaresDeseados) {
        List<Lugar> todos = lugarRepository.findAll();

        List<Lugar> seleccionados = todos.stream()
                .filter(l -> lugaresDeseados.contains(l.getId()))
                .toList();

        int maxLugares = Math.min(seleccionados.size(), 4); // podés cambiar el límite
        int tiempoDisponible = usuario.getTiempoDisponibleMin();

        List<Ruta> rutas = Backtracking.generarItinerarios(seleccionados, maxLugares, tiempoDisponible);

        List<List<String>> combinaciones = new ArrayList<>();
        for (Ruta r : rutas) {
            combinaciones.add(r.getLugares().stream().map(Lugar::getNombre).toList());
        }

        return new BacktrackingResultadoDTO(combinaciones);
    }


    /** Branch & Bound: devuelve la mejor combinación por popularidad con restricciones */
    public BranchAndBoundResultadoDTO optimizarBranchAndBound(
            List<String> lugaresDeseados,
            double tiempoDisponibleMin,
            double presupuestoMax
    ) {
        // Traer lugares desde Neo4j y filtrar por los solicitados
        List<Lugar> todos = lugarRepository.findAll();
        List<Lugar> seleccionables = todos.stream()
                .filter(l -> lugaresDeseados.contains(l.getId()))
                .toList();

        BranchAndBound.Resultado res = BranchAndBound.optimizar(seleccionables, tiempoDisponibleMin, presupuestoMax);

        List<String> nombres = res.seleccion.stream().map(Lugar::getNombre).collect(Collectors.toList());
        return new BranchAndBoundResultadoDTO(nombres, res.popularidadTotal, res.tiempoTotal, res.costoTotal);
    }

}
