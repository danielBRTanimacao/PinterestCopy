FROM maven:3.9.16-amazoncorretto-25-alpine AS builder

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:26.0.1_8-jre-ubi10-minimal

WORKDIR /pinstack

COPY --from=builder /app/target/api-0.0.1-SNAPSHOT.jar /api.jar

ENTRYPOINT ["java","-jar","/api.jar"]

EXPOSE 8080