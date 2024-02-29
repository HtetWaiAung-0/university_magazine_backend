FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /usr/src/app   # Set a working directory
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /usr/src/app/target/university_magazine_backend-0.0.1-SNAPSHOT.jar /usr/src/app/university_magazine_backend.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","university_magazine_backend.jar"]
