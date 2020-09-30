FROM openjdk:8-jdk-alpine

EXPOSE ${CONFIGURATION_SERVER_PORT}
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
COPY build/libs/ConfigurationServer.jar .

ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.2.1/wait /wait
RUN chmod +x /wait

CMD /wait && java -jar -Dserver.port=${CONFIGURATION_SERVER_PORT} -Ddiscovery.instance.hostname=${DISCOVERY_SERVER_HOST} -Ddiscovery.instance.port=${DISCOVERY_SERVER_PORT} -Dspring.cloud.config.server.git.uri=${CONFIG_URI} /app/ConfigurationServer.jar