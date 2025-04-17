# Stage 1: Build the application with Maven
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Prepare a lightweight image to run the built application
FROM eclipse-temurin:21.0.4_7-jdk-alpine

WORKDIR /app
COPY --from=builder /app/target/*.jar /app/vnpay-payment.jar
ENTRYPOINT ["java", "-jar", "/app/vnpay-payment.jar"]
