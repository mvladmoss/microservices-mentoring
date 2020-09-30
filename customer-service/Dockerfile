FROM openjdk:8-jdk-alpine
EXPOSE 8083
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY build/libs/customer-service.jar .

ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.2.1/wait /wait
RUN chmod +x /wait

CMD /wait && java -jar -Dspring.datasource.url=${CUSTOMER_SERVICE_DB_URL} -Dspring.datasource.username=${CUSTOMER_SERVICE_DATABASE_USERNAME} -Dspring.datasource.password=${CUSTOMER_SERVICE_DATABASE_PASSWORD} -Ddiscovery.instance.hostname=${DISCOVERY_SERVER_HOST} -Ddiscovery.instance.port=${DISCOVERY_SERVER_PORT} -Dconfiguration.instance.hostname=${CONFIGURATION_SERVER_HOST} -Dconfiguration.instance.port=${CONFIGURATION_SERVER_PORT} /app/customer-service.jar