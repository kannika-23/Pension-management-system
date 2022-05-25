FROM maven:3.8.1-openjdk-17-slim  AS mvnbuild
WORKDIR ./
COPY src /project/src
COPY pom.xml /project
RUN mvn -file /project/pom.xml clean package -Dmaven.test.skip

FROM eclipse-temurin:17-jre-alpine
WORKDIR ./app
COPY --from=mvnbuild /project/target/pmp-auth-server-executable.jar ./app
EXPOSE 8080
ENTRYPOINT ["java","-jar","pmp-auth-server-executable.jar","-Dspring.config.additional-location=/application.yml"]