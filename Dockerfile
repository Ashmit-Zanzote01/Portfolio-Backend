# Use Maven with OpenJDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Use OpenJDK 21 for runtime
FROM openjdk:21-jdk  # ✅ Corrected to Java 21
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
