# mvn clean package -DskipTests
# cp target/tom-0.0.1-SNAPSHOT.jar src/main/docker

FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]