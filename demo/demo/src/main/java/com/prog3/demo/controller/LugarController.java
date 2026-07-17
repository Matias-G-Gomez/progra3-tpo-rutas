package com.prog3.demo.controller;


import com.prog3.demo.dto.DfsResultadoDTO;
import com.prog3.demo.dto.LugarDistanciaDTO;
import com.prog3.demo.dto.LugarSimpleDTO;
import com.prog3.demo.model.Lugar;
import com.prog3.demo.service.PlanificadorRutasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api/lugares")
public class LugarController {


    private final PlanificadorRutasService service;


    public LugarController(PlanificadorRutasService service) {
        this.service = service;
    }


    @GetMapping
    public ResponseEntity<List<Lugar>> listarTodos() {
        List<Lugar> lugares = service.obtenerTodosLosLugares();
        return ResponseEntity.ok(lugares);
    }


    @GetMapping("/alcanzables")
    public ResponseEntity<List<LugarDistanciaDTO>> alcanzables(
            @RequestParam String origen,
            @RequestParam double radioKm
    ) {
        return ResponseEntity.ok(service.bfsLugaresAlcanzables(origen, radioKm));
    }



    @GetMapping("/alcanzablesPorSaltos")
    public ResponseEntity<List<LugarDistanciaDTO>> alcanzablesPorSaltos(
            @RequestParam String origen,
            @RequestParam int saltos
    ) {
        return ResponseEntity.ok(service.bfsPorSaltos(origen, saltos));
    }


    // Prim
    @GetMapping("/redMinima")
    public List<String> redMinima() {
        return service.generarRedMinimaPrim();
    }

    // Kruskal
    @GetMapping("/redMinimaKruskal")
    public List<String> redMinimaKruskal() {
        return service.generarRedMinimaKruskal();
    }



    // Quicksort
    // Ascendente o Descendente
    @GetMapping("/ordenar")
    public ResponseEntity<List<Lugar>> ordenar(
            @RequestParam String criterio,
            @RequestParam(defaultValue = "asc") String orden
    ) {
        List<Lugar> lugares = service.obtenerTodosLosLugares();
        List<Lugar> ordenados = service.ordenarLugares(lugares, criterio, orden.equalsIgnoreCase("asc"));
        return ResponseEntity.ok(ordenados);
    }

    // Mergesort Wrapper
    @GetMapping("/ordenarMerge")
    public ResponseEntity<List<Lugar>> ordenarMerge(
            @RequestParam String criterio,
            @RequestParam(defaultValue = "asc") String orden
    ) {
        List<Lugar> lugares = service.obtenerTodosLosLugares();
        List<Lugar> ordenados = service.ordenarLugaresMerge(lugares, criterio, orden.equalsIgnoreCase("asc"));
        return ResponseEntity.ok(ordenados);
    }

    // DFS
    @GetMapping("/dfs")
    public ResponseEntity<List<DfsResultadoDTO>> dfs(@RequestParam String origen) {
        return ResponseEntity.ok(service.dfsLugares(origen));
    }




}


