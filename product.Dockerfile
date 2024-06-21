FROM openjdk:23-ea-17-jdk-slim

WORKDIR /app

COPY product-service/target/product-service.jar app.jar

ENV HW_OBS_SAK=ZXwgRdTdsCgGvSg7rt9BVO7rtk7U7aZomR50zPNa \
    HW_OBS_AK=AFCZ0FDVDMHTUJUXE7AC

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]