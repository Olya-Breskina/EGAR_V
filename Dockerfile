#FROM openjdk:17
#COPY target/*.jar app.jar
#ENTRYPOINT ["java", "-jar", "app.jar"]
#EXPOSE 8081

# Build stage
#
FROM maven:3.8.4-openjdk-17-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests=true
#-DskipTests=true без тестов
#
# Package stage
#
FROM eclipse-temurin:17-jre-alpine
COPY --from=build /home/app/target/*.jar /usr/local/lib/app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]

