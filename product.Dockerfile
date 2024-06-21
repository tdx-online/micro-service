FROM openjdk:23-ea-17-jdk-slim

WORKDIR /app

COPY product-service/target/product-service.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]