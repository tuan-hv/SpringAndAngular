# For Java 8, try this
FROM openjdk:8-jdk-alpine

WORKDIR /opt/app

ARG JAR_FILE=target/spring-boot-web.jar

# cp spring-boot-web.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar
# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]