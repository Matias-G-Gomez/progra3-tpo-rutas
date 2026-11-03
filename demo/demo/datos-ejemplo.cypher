// Datos de ejemplo para el Planificador de Rutas
// Pegá todo este script en Neo4j Browser (http://localhost:7474) y ejecutalo.
// Borra los datos anteriores y carga 8 lugares de Buenos Aires con sus conexiones.

MATCH (n) DETACH DELETE n;

UNWIND [
  {id: 'A', nombre: 'Obelisco',               tipo: 'monumento',   popularidad: 9.5, precioEntrada: 0,  tiempoRecomendadoMin: 30},
  {id: 'B', nombre: 'Teatro Colón',           tipo: 'teatro',      popularidad: 9.8, precioEntrada: 15, tiempoRecomendadoMin: 90},
  {id: 'C', nombre: 'Plaza de Mayo',          tipo: 'plaza',       popularidad: 8.7, precioEntrada: 0,  tiempoRecomendadoMin: 40},
  {id: 'D', nombre: 'Caminito',               tipo: 'barrio',      popularidad: 9.2, precioEntrada: 0,  tiempoRecomendadoMin: 60},
  {id: 'E', nombre: 'MALBA',                  tipo: 'museo',       popularidad: 9.0, precioEntrada: 12, tiempoRecomendadoMin: 120},
  {id: 'F', nombre: 'Jardín Japonés',         tipo: 'parque',      popularidad: 8.9, precioEntrada: 8,  tiempoRecomendadoMin: 60},
  {id: 'G', nombre: 'Cementerio de Recoleta', tipo: 'monumento',   popularidad: 9.1, precioEntrada: 10, tiempoRecomendadoMin: 75},
  {id: 'H', nombre: 'Puerto Madero',          tipo: 'restaurante', popularidad: 8.8, precioEntrada: 20, tiempoRecomendadoMin: 90}
] AS l
CREATE (:Lugar {
  id: l.id, nombre: l.nombre, tipo: l.tipo, popularidad: l.popularidad,
  precioEntrada: l.precioEntrada, tiempoRecomendadoMin: l.tiempoRecomendadoMin
});

// Cada conexión se crea en los dos sentidos, porque se puede ir y volver
UNWIND [
  {a: 'A', b: 'B', km: 0.6, min: 8,  costo: 1.0},
  {a: 'A', b: 'C', km: 1.5, min: 12, costo: 1.5},
  {a: 'A', b: 'G', km: 2.5, min: 15, costo: 2.0},
  {a: 'B', b: 'G', km: 2.2, min: 14, costo: 2.0},
  {a: 'C', b: 'H', km: 1.2, min: 10, costo: 1.5},
  {a: 'C', b: 'D', km: 4.0, min: 20, costo: 3.0},
  {a: 'H', b: 'D', km: 3.5, min: 18, costo: 2.5},
  {a: 'G', b: 'E', km: 1.8, min: 10, costo: 1.5},
  {a: 'E', b: 'F', km: 1.3, min: 8,  costo: 1.0},
  {a: 'G', b: 'F', km: 2.6, min: 14, costo: 2.0}
] AS c
MATCH (x:Lugar {id: c.a}), (y:Lugar {id: c.b})
CREATE (x)-[:CONECTA {distanciaKm: c.km, tiempoMin: c.min, costo: c.costo}]->(y),
       (y)-[:CONECTA {distanciaKm: c.km, tiempoMin: c.min, costo: c.costo}]->(x);

// Para ver el grafo dibujado (ideal para una captura):
// MATCH (n:Lugar)-[r:CONECTA]->(m) RETURN n, r, m
