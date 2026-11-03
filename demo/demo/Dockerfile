# ---- Etapa 1: compilar el proyecto con Maven ----
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Primero solo el pom.xml, para que Docker reutilice las dependencias descargadas
COPY pom.xml .
RUN mvn -q -B dependency:go-offline

# Después el código, y se genera el .jar sin correr los tests
COPY src ./src
RUN mvn -q -B package -DskipTests

# ---- Etapa 2: imagen liviana solo para ejecutar ----
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
# Render indica el puerto en la variable PORT; en local se usa 8080
CMD ["sh", "-c", "java -Xmx350m -Dserver.port=${PORT:-8080} -jar app.jar"]
