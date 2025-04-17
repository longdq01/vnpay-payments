# Stage 1: Build the application with Maven
FROM maven:3.9.9-eclipse-temurin-11 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Prepare a lightweight image to run the built application
FROM eclipse-temurin:11.0.24_8-jdk-alpine

WORKDIR /app
COPY --from=builder /app/target/*.jar /app/vnpay-payment.jar
ENTRYPOINT ["java", "-jar", "/app/vnpay-payment.jar"]
