FROM openjdk:8-jdk-alpine
EXPOSE 8081
WORKDIR /app
COPY ././gradlew .
COPY ././gradle gradle
COPY ././build.gradle .
COPY ././settings.gradle .
COPY ././src src
COPY build/libs/shop-service.jar .

ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.2.1/wait /wait
RUN chmod +x /wait

CMD /wait && java -jar -Dspring.datasource.url=${ORDER_SERVICE_DB_URL} -Dspring.datasource.username=${ORDER_SERVICE_DATABASE_USERNAME} -Dspring.datasource.password=${ORDER_SERVICE_DATABASE_PASSWORD} /app/shop-service.jar