FROM openjdk:17-jdk-alpine
MAINTAINER Burduzhan Ruslan
VOLUME /main-app
ADD target/production_flow_service-0.0.1-SNAPSHOT.jar production_flow_service.jar
EXPOSE 8081
COPY src/main/resources/Images src/main/resources/Images
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=prod", "production_flow_service.jar"]
