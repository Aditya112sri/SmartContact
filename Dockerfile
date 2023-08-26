FROM maven:3.8.4-openjdk-17 as BUILD
COPY . .
RUN mvn clean package DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/SmartContactManager-0.0.1-SNAPSHOT.jar contact.jar
EXPOSE 8080
ENTRYPOINT [ "java","-jar","conatct.jar" ]