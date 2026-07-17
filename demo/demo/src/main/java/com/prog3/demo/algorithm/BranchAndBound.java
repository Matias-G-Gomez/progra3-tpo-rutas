package com.prog3.demo.algorithm;

import com.prog3.demo.model.Lugar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Selecciona el subconjunto de lugares que maximiza la popularidad total
 * sin exceder tiempo disponible ni presupuesto (Branch & Bound).
 */
public class BranchAndBound {

    /** Resultado interno del algoritmo (no DTO) */
    public static class Resultado {
        public final List<Lugar> seleccion;
        public final double popularidadTotal;
        public final double tiempoTotal;
        public final double costoTotal;

        public Resultado(List<Lugar> seleccion, double popularidadTotal, double tiempoTotal, double costoTotal) {
            this.seleccion = seleccion;
            this.popularidadTotal = popularidadTotal;
            this.tiempoTotal = tiempoTotal;
            this.costoTotal = costoTotal;
        }
    }

    public static Resultado optimizar(List<Lugar> lugares, double tiempoMax, double presupuestoMax) {
        // (opcional) ordenar por densidad de “valor” para que el bound sea mejor
        lugares.sort(Comparator.comparingDouble(
                l -> - (l.getPopularidad() / Math.max(1.0, l.getTiempoRecomendadoMin()))
        ));

        Resultado mejor = new Resultado(new ArrayList<>(), 0, 0, 0);
        backtrack(
                0,
                new ArrayList<>(),
                0, 0, 0,
                lugares,
                tiempoMax,
                presupuestoMax,
                mejor
        );
        return mejor;
    }

    private static void backtrack(
            int idx,
            List<Lugar> actual,
            double popAct,
            double tiempoAct,
            double costoAct,
            List<Lugar> todos,
            double tiempoMax,
            double presupuestoMax,
            Resultado mejor
    ) {
        // factibilidad
        if (tiempoAct > tiempoMax || costoAct > presupuestoMax) return;

        // actualizar mejor
        if (popAct > mejor.popularidadTotal) {
            mejor.seleccion.clear();
            mejor.seleccion.addAll(actual);
            // reemplazamos el objeto "mejor" entero
            mejor = replaceResultado(mejor, new Resultado(new ArrayList<>(actual), popAct, tiempoAct, costoAct));
        }

        // poda por cota superior
        double bound = popAct + boundRestante(idx, todos, tiempoAct, costoAct, tiempoMax, presupuestoMax);
        if (bound <= mejor.popularidadTotal) return;

        // sin más elementos
        if (idx >= todos.size()) return;

        // decidir incluir el lugar idx
        Lugar l = todos.get(idx);

        actual.add(l);
        backtrack(
                idx + 1, actual,
                popAct + l.getPopularidad(),
                tiempoAct + l.getTiempoRecomendadoMin(),
                costoAct + l.getPrecioEntrada(),
                todos, tiempoMax, presupuestoMax, mejor
        );
        actual.remove(actual.size() - 1);

        // decidir NO incluirlo
        backtrack(
                idx + 1, actual,
                popAct, tiempoAct, costoAct,
                todos, tiempoMax, presupuestoMax, mejor
        );
    }

    private static double boundRestante(
            int idx,
            List<Lugar> todos,
            double tiempoAct,
            double costoAct,
            double tiempoMax,
            double presupuestoMax
    ) {
        double tiempoRest = tiempoMax - tiempoAct;
        double presuRest  = presupuestoMax - costoAct;
        double suma = 0;

        for (int i = idx; i < todos.size(); i++) {
            Lugar l = todos.get(i);
            if (l.getTiempoRecomendadoMin() <= tiempoRest && l.getPrecioEntrada() <= presuRest) {
                suma += l.getPopularidad();
                tiempoRest -= l.getTiempoRecomendadoMin();
                presuRest  -= l.getPrecioEntrada();
            }
        }
        return suma;
    }

    /** Utilidad para “reemplazar” campos finales del mejor */
    private static Resultado replaceResultado(Resultado target, Resultado src) {
        target.seleccion.clear();
        target.seleccion.addAll(src.seleccion);
        return new Resultado(src.seleccion, src.popularidadTotal, src.tiempoTotal, src.costoTotal);
    }
}
