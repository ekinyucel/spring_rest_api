FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/springrest-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "/app.jar"]