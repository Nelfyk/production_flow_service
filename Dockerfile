FROM openjdk:17-jdk-alpine
MAINTAINER Burduzhan Ruslan
VOLUME /main-app
ADD target/production_flow_service-0.0.1-SNAPSHOT.jar production_flow_service.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","production_flow_service.jar"]




#git remote rm origin
#git remote add origin <ссылка>