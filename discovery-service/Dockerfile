FROM openjdk:8-jdk-alpine

EXPOSE ${DISCOVERY_SERVER_PORT}
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
COPY build/libs/DiscoveryServer.jar .

CMD java -jar -Dserver.port=${DISCOVERY_SERVER_PORT} /app/DiscoveryServer.jar