FROM maven:3.9-eclipse-temurin-21 AS buildstage

WORKDIR /app

COPY pom.xml .
COPY src /app/src
COPY Wallet_DLNA4QPL3D91304G /app/wallet/

ENV TNS_ADMIN=/app/wallet

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk

COPY --from=buildstage /app/target/ordenes-mascotas-0.0.1-SNAPSHOT.jar /app/ordenes-mascotas.jar
COPY Wallet_DLNA4QPL3D91304G /app/wallet/

ENV TNS_ADMIN=/app/wallet

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/ordenes-mascotas.jar"]
