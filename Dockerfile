# 1. AŞAMA: Build (Derleme)
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# 2. AŞAMA: Run (Çalıştırma) - Burayı değiştirdik!
FROM eclipse-temurin:17-jdk-focal
WORKDIR /app
COPY --from=build /target/linc-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
