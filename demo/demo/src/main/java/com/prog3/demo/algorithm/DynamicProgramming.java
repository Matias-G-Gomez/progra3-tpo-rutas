package com.prog3.demo.algorithm;

import com.prog3.demo.model.Lugar;
import java.util.*;

public class DynamicProgramming {

    /**
     * Resuelve un problema tipo knapsack:
     * Maximiza la suma de popularidad sin superar el tiempo disponible
     */
    public static List<Lugar> optimizar(List<Lugar> lugares, int tiempoMax) {
        int n = lugares.size();
        double[][] dp = new double[n + 1][tiempoMax + 1];

        // construir la tabla DP
        for (int i = 1; i <= n; i++) {
            Lugar lugar = lugares.get(i - 1);
            int tiempo = lugar.getTiempoRecomendadoMin();
            double valor = lugar.getPopularidad();

            for (int t = 0; t <= tiempoMax; t++) {
                if (tiempo <= t) {
                    dp[i][t] = Math.max(valor + dp[i - 1][t - tiempo], dp[i - 1][t]);
                } else {
                    dp[i][t] = dp[i - 1][t];
                }
            }
        }

        // reconstrucción del camino óptimo
        List<Lugar> seleccionados = new ArrayList<>();
        int t = tiempoMax;
        for (int i = n; i > 0; i--) {
            if (dp[i][t] != dp[i - 1][t]) {
                Lugar l = lugares.get(i - 1);
                seleccionados.add(l);
                t -= l.getTiempoRecomendadoMin();
            }
        }

        Collections.reverse(seleccionados);
        return seleccionados;
    }
}
