# 1. AŞAMA: Build (Derleme)
# Maven ve Java 17 içeren resmi imajı kullanıyoruz
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Proje dosyalarını konteyner içine kopyala
COPY . .

# Testleri atlayarak projeyi derle (Canlıya çıkarken test veritabanı olmadığı için skipTests şart)
RUN mvn clean package -DskipTests

# 2. AŞAMA: Run (Çalıştırma)
# Sadece çalıştırılabilir JDK'yı içeren hafif imaj
FROM openjdk:17-jdk-slim

# Build aşamasında oluşan JAR dosyasını buraya al ve adını app.jar yap
# pom.xml'deki artifactId ve version bilgine göre dosya adı: linc-0.0.1-SNAPSHOT.jar
COPY --from=build /target/linc-0.0.1-SNAPSHOT.jar app.jar

# Render'ın kullanacağı portu dışarı aç
EXPOSE 8080

# Uygulamayı başlat
ENTRYPOINT ["java", "-jar", "app.jar"]
