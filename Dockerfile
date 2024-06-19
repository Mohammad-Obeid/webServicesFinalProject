FROM openjdk:21-jdk-oracle

WORKDIR /app

COPY target/WebServicesFinalProject-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]