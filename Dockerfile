# ETAPA 1: Construcción (Build)
# Usamos una imagen que ya incluye Maven y JDK 17 para evitar errores de archivos faltantes
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copiamos solo el archivo de configuración de dependencias primero para aprovechar el cache de Docker
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos el código fuente y compilamos el proyecto
COPY src ./src
RUN mvn clean package -DskipTests

# ETAPA 2: Ejecución (Run)
# Usamos una imagen ligera y mantenida de Eclipse Temurin para correr la app
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copiamos solo el archivo JAR generado en la etapa anterior
# El nombre del JAR debe coincidir con el definido en tu pom.xml (backend-1.0.0.jar)
COPY --from=build /app/target/backend-1.0.0.jar app.jar

# Exponemos el puerto configurado en tu aplicación
EXPOSE 8080

# Comando para iniciar la aplicación con el perfil de producción
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]