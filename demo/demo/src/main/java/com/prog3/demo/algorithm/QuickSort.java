package com.prog3.demo.algorithm;

import com.prog3.demo.model.Lugar;
import java.util.List;

public class QuickSort {

    public static void quickSort(List<Lugar> lista, String criterio, boolean ascendente) {
        if (lista == null || lista.size() <= 1) return;
        quickSortRec(lista, 0, lista.size() - 1, criterio, ascendente);
    }

    private static void quickSortRec(List<Lugar> lista, int low, int high, String criterio, boolean ascendente) {
        if (low < high) {
            int pi = particionar(lista, low, high, criterio, ascendente);
            quickSortRec(lista, low, pi - 1, criterio, ascendente);
            quickSortRec(lista, pi + 1, high, criterio, ascendente);
        }
    }

    private static int particionar(List<Lugar> lista, int low, int high, String criterio, boolean ascendente) {
        Lugar pivot = lista.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (comparar(lista.get(j), pivot, criterio, ascendente)) {
                i++;
                Lugar temp = lista.get(i);
                lista.set(i, lista.get(j));
                lista.set(j, temp);
            }
        }

        Lugar temp = lista.get(i + 1);
        lista.set(i + 1, lista.get(high));
        lista.set(high, temp);

        return i + 1;
    }

    private static boolean comparar(Lugar a, Lugar b, String criterio, boolean ascendente) {
        int resultado = 0;
        switch (criterio.toLowerCase()) {
            case "popularidad":
                resultado = Double.compare(a.getPopularidad(), b.getPopularidad());
                break;
            case "precio":
                resultado = Double.compare(a.getPrecioEntrada(), b.getPrecioEntrada());
                break;
            case "tiempo":
                resultado = Integer.compare(a.getTiempoRecomendadoMin(), b.getTiempoRecomendadoMin());
                break;
            case "nombre":
                resultado = a.getNombre().compareToIgnoreCase(b.getNombre());
                break;
        }
        return ascendente ? (resultado <= 0) : (resultado > 0);
    }
}
