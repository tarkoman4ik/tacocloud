FROM openjdk:17-jdk-slim
EXPOSE 8080
COPY target/tacocloud-0.0.1-SNAPSHOT.jar tacoapp.jar
ENTRYPOINT ["java","-jar","/tacoapp.jar"]