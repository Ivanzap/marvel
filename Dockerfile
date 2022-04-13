FROM maven:3.8.4-openjdk-17 AS MAVEN_BUILD

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:18-alpine3.14

ARG JAR_FILE=./app/target/*.jar

COPY --from=MAVEN_BUILD ${JAR_FILE} /app.jar

CMD ["java", "-jar", "/app.jar"]