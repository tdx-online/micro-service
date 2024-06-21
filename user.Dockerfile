FROM openjdk:23-ea-17-jdk-slim

WORKDIR /app

COPY user-service/target/user-service.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]