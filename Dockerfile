# Etapa 1: Compilación
FROM maven:3.9.4-eclipse-temurin-17 AS builder

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo pom.xml y descargar las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el resto del proyecto
COPY src ./src

# Compilar el proyecto, generando el archivo JAR
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final para ejecución
FROM eclipse-temurin:17-jre

# Crear un directorio para la aplicación
WORKDIR /app

# Copiar el archivo JAR generado desde la etapa de compilación
COPY --from=builder /app/target/NoteApp-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto estándar de Spring Boot
EXPOSE 8080

# Configuración opcional para parámetros de JVM
ENV JAVA_OPTS=""

# Comando para ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
