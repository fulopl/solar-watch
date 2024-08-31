FROM eclipse-temurin:17-jre
ARG JAR_FILE=../../target/*.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]


#FROM eclipse-temurin:21-jre-alpine
#WORKDIR /tmp
#COPY target/*.jar app.jar
#ENTRYPOINT ["java","-jar","app.jar"]