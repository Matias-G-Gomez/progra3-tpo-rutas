package com.prog3.demo.controller;


import com.prog3.demo.dto.BacktrackingResultadoDTO;
import com.prog3.demo.dto.BranchAndBoundResultadoDTO;
import com.prog3.demo.dto.DynamicProgrammingResultadoDTO;
import com.prog3.demo.model.Ruta;
import com.prog3.demo.model.Usuario;
import com.prog3.demo.service.PlanificadorRutasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
public class RutaController {


    private final PlanificadorRutasService service;


    public RutaController(PlanificadorRutasService service) {
        this.service = service;
    }


    @GetMapping("/ruta/minima")
    public Ruta rutaMinima(@RequestParam String origenId, @RequestParam String destinoId) {
        return service.calcularRutaMinima(origenId, destinoId);
    }


    @GetMapping("/redMinima")
    public List<String> redMinima() {
        return service.generarRedMinimaPrim();
    }



    @PostMapping("/greedy")
    public ResponseEntity<Ruta> rutaGreedy(@RequestBody GreedyRequest req) {
        Ruta r = service.generarRutaGreedy(req.origenId, req.lugaresDeseados);
        return ResponseEntity.ok(r);
    }


    @PostMapping("/optimizar/dp")
    public ResponseEntity<DynamicProgrammingResultadoDTO> optimizarDP(@RequestBody OptimizarRequest req) {
        DynamicProgrammingResultadoDTO r = service.optimizarItinerarioDP(req.usuario, req.lugaresDeseados);
        return ResponseEntity.ok(r);
    }



    @PostMapping("/backtracking")
    public ResponseEntity<BacktrackingResultadoDTO> backtracking(@RequestBody OptimizarRequest req) {
        BacktrackingResultadoDTO res = service.generarItinerariosBacktracking(req.usuario, req.lugaresDeseados);
        return ResponseEntity.ok(res);
    }



    // ===== Branch & Bound =====
    @PostMapping("/branchbound")
    public ResponseEntity<BranchAndBoundResultadoDTO> branchAndBound(@RequestBody BranchAndBoundRequest req) {
        BranchAndBoundResultadoDTO dto = service.optimizarBranchAndBound(
                req.lugaresDeseados,
                req.tiempoDisponibleMin,
                req.presupuestoMax
        );
        return ResponseEntity.ok(dto);
    }

    /** Request para Branch & Bound */
    public static class BranchAndBoundRequest {
        public List<String> lugaresDeseados;   // ej: ["A","C","E","G"]
        public double tiempoDisponibleMin;     // ej: 180.0
        public double presupuestoMax;          // ej: 20.0
    }




    // DTOs internos para requests
    public static class GreedyRequest {
        public String origenId;
        public List<String> lugaresDeseados;
    }


    public static class OptimizarRequest {
        public Usuario usuario;
        public List<String> lugaresDeseados;
    }
}
