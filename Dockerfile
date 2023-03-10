FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /home/app
ADD pom.xml .
RUN mvn -f ./pom.xml clean package
COPY src ./src

FROM openjdk:17-slim
COPY --from=build  target/*.jar /usr/local/lib/drones.jar
EXPOSE 8050
ENTRYPOINT ["java", "-jar", "/usr/local/lib/drones.jar"]
