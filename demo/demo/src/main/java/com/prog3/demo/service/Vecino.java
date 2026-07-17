package com.prog3.demo.service;

public record Vecino(String destinoId, double distanciaKm) { }
// Al usar "record" se genera automáticamente los getters (porque están implícitos)