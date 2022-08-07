#FROM openjdk:11
#ARG JAR_FILE=target/*.jar
#EXPOSE 80
#COPY ${JAR_FILE} DoxaEntityApplication.jar
#ENTRYPOINT ["java","-jar","DoxaEntityApplication.jar"]

#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
COPY target/*.jar /home/app/target/*.jar
#RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip=true

#
# Package stage
#
FROM openjdk:11-jre-slim
ARG JAR_FILE=/home/app/target/*.jar
COPY --from=build ${JAR_FILE} Oauth2Application.jar
EXPOSE 80
ENTRYPOINT ["java","-jar","Oauth2Application.jar"]