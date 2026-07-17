package com.prog3.demo.algorithm;

import com.prog3.demo.model.Lugar;
import java.util.*;

public class MergeSort {

    public static List<Lugar> mergeSort(List<Lugar> lista, String criterio, boolean ascendente) {
        if (lista.size() <= 1) return lista;

        int mid = lista.size() / 2;
        List<Lugar> izquierda = mergeSort(new ArrayList<>(lista.subList(0, mid)), criterio, ascendente);
        List<Lugar> derecha = mergeSort(new ArrayList<>(lista.subList(mid, lista.size())), criterio, ascendente);

        return merge(izquierda, derecha, criterio, ascendente);
    }

    private static List<Lugar> merge(List<Lugar> izquierda, List<Lugar> derecha, String criterio, boolean ascendente) {
        List<Lugar> resultado = new ArrayList<>();
        int i = 0, j = 0;

        while (i < izquierda.size() && j < derecha.size()) {
            if (comparar(izquierda.get(i), derecha.get(j), criterio, ascendente)) {
                resultado.add(izquierda.get(i++));
            } else {
                resultado.add(derecha.get(j++));
            }
        }

        while (i < izquierda.size()) resultado.add(izquierda.get(i++));
        while (j < derecha.size()) resultado.add(derecha.get(j++));

        return resultado;
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
        return ascendente ? resultado <= 0 : resultado > 0;
    }
}
