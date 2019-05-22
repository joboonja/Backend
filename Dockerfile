# build project with maven
FROM maven:3.6.1-jdk-8
WORKDIR /app
COPY src/ /app/src/
COPY pom.xml /app/pom.xml
RUN mvn clean
RUN mvn package


# MySQL config 
FROM mysql:8.0
ENV MYSQL_ROOT_PASSWORD=1377731
ENV MYSQL_DATABASE=joboonja

FROM tomcat:9.0.20-jre11
RUN ls
RUN cp /app/target/Joboonja-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/webapp
RUN mv /usr/local/tomcat/webapps/webapp/Joboonja-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/webapp/ROOT.war

