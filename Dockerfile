# build project with maven
FROM maven:3.6.1-jdk-8 AS maven-app
WORKDIR /app
COPY src/ /app/src/
COPY pom.xml /app/pom.xml
RUN mvn clean
RUN mvn package
VOLUME /app

# MySQL config 
FROM mysql:8.0 AS mysql-app
ENV MYSQL_ROOT_PASSWORD=1377731
ENV MYSQL_DATABASE=joboonja

FROM tomcat:9.0.20-jre11 As tomcat-app
RUN ls -a webapps
COPY --from=maven-app /app/target/Joboonja-1.0-SNAPSHOT.war /webapps/ROOT.war
RUN ls -a webapps

