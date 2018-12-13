FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY ./target/springrest-0.0.1-SNAPSHOT.jar /app
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "springrest-0.0.1-SNAPSHOT.jar /app"]