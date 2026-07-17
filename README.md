# Planificador de Rutas con Algoritmos de Grafos

Backend desarrollado en Spring Boot para la materia Programación 3 (UADE). El sistema modela un conjunto de lugares turísticos y sus conexiones como un grafo, y expone una API REST que permite calcular rutas, redes óptimas e itinerarios utilizando distintos algoritmos clásicos de grafos y optimización.

## Descripción del proyecto

Cada lugar (museo, parque, restaurante, etc.) es un nodo del grafo, y cada conexión entre lugares tiene una distancia, un tiempo de viaje y un costo asociado. Sobre ese grafo, la aplicación permite:

- Encontrar el camino más corto entre dos lugares.
- Explorar qué lugares son alcanzables desde un origen, ya sea por distancia o por cantidad de saltos.
- Calcular la red de conexión mínima entre todos los lugares.
- Generar itinerarios optimizados según el tiempo y el presupuesto disponible.
- Ordenar y filtrar lugares según distintos criterios.

## Tecnologías utilizadas

- Java 17
- Spring Boot 3.5
- Spring Data Neo4j (persistencia del grafo en una base de datos Neo4j)
- Maven

## Algoritmos implementados

| Algoritmo | Uso en el proyecto |
|---|---|
| BFS (Búsqueda en anchura) | Encontrar lugares alcanzables desde un origen, por radio o por cantidad de saltos |
| DFS (Búsqueda en profundidad) | Recorrido exploratorio del grafo de lugares |
| Dijkstra | Cálculo de la ruta de menor costo entre dos lugares |
| Prim | Generación de la red de conexión mínima (versión 1) |
| Kruskal | Generación de la red de conexión mínima (versión 2) |
| Union-Find | Estructura de apoyo utilizada por Kruskal |
| Greedy | Generación rápida de una ruta que prioriza los lugares deseados |
| Programación Dinámica | Optimización de itinerarios según tiempo y lugares deseados |
| Backtracking | Generación de todas las combinaciones posibles de itinerarios válidos |
| Branch and Bound | Optimización de itinerarios respetando restricciones de tiempo y presupuesto |
| QuickSort / MergeSort | Ordenamiento de lugares según distintos criterios |

## Endpoints principales

### Lugares (`/api/lugares`)

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/lugares` | Lista todos los lugares cargados |
| GET | `/api/lugares/alcanzables?origen={id}&radioKm={km}` | Lugares alcanzables dentro de un radio, usando BFS |
| GET | `/api/lugares/alcanzablesPorSaltos?origen={id}&saltos={n}` | Lugares alcanzables en una cantidad determinada de saltos |
| GET | `/api/lugares/redMinima` | Red de conexión mínima usando Prim |
| GET | `/api/lugares/redMinimaKruskal` | Red de conexión mínima usando Kruskal |
| GET | `/api/lugares/ordenar?criterio={campo}&orden={asc/desc}` | Ordena los lugares por un criterio (QuickSort) |
| GET | `/api/lugares/ordenarMerge?criterio={campo}&orden={asc/desc}` | Ordena los lugares por un criterio (MergeSort) |
| GET | `/api/lugares/dfs?origen={id}` | Recorrido en profundidad desde un lugar |

### Rutas

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/ruta/minima?origenId={id}&destinoId={id}` | Camino más corto entre dos lugares (Dijkstra) |
| GET | `/redMinima` | Red de conexión mínima (Prim) |
| POST | `/greedy` | Ruta que visita los lugares deseados con criterio Greedy |
| POST | `/optimizar/dp` | Itinerario optimizado con Programación Dinámica |
| POST | `/backtracking` | Todas las combinaciones de itinerarios posibles |
| POST | `/branchbound` | Itinerario óptimo respetando tiempo y presupuesto máximo |

## Modelo de datos

- **Lugar**: id, nombre, tipo, popularidad, precio de entrada, tiempo recomendado de visita.
- **Conexión**: relación entre dos lugares, con distancia (km), tiempo (min) y costo asociado.

## Cómo ejecutar el proyecto

### Requisitos previos

- Java 17 o superior
- Maven (o usar el wrapper incluido, `mvnw`)
- Una instancia de Neo4j corriendo localmente (por defecto en `bolt://localhost:7687`)

### Pasos

1. Cloná el repositorio:
   ```bash
   git clone https://github.com/Matias-G-Gomez/progra3-tpo-rutas.git
   ```

2. Ingresá a la carpeta del proyecto Spring Boot:
   ```bash
   cd progra3-tpo-rutas/demo/demo
   ```

3. Configurá la conexión a Neo4j en `src/main/resources/application.properties` con tus propias credenciales.

4. Ejecutá la aplicación:
   ```bash
   ./mvnw spring-boot:run
   ```

5. La API queda disponible en `http://localhost:8080`.

## Estado del proyecto

Proyecto desarrollado como trabajo práctico para la cátedra de Programación 3 (UADE, 2025).
