# mvn clean package -DskipTests
# cp target/tom-0.0.1-SNAPSHOT.jar src/main/docker
# Docker Build Maven Stage
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /tomapp
COPY ./ /tomapp
RUN mvn clean install -DskipTests
# Run spring boot in Docker

FROM openjdk:17
COPY --from=build /tomapp/target/*.jar application.jar
ENV PORT 8081
EXPOSE $PORT
ENTRYPOINT ["java", "-jar","-Dserver.port=${PORT}", "application.jar"]

# mvn clean package -DskipTests
# cp target/tom-0.0.1-SNAPSHOT.jar src/main/docker
#
#FROM openjdk:17
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} application.jar
#ENV PORT 8081
#EXPOSE $PORT
#ENTRYPOINT ["java", "-jar","-Dserver.port=${PORT}", "application.jar"]