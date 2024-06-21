FROM openjdk:23-ea-17-jdk-slim

WORKDIR /app

COPY eureka-service/target/eureka-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8079

ENTRYPOINT ["java", "-jar", "app.jar"]