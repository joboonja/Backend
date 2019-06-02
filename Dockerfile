# build project with maven
FROM maven:3.6.1-jdk-8 AS maven-app
WORKDIR /app
COPY src/ /app/src/
COPY pom.xml /app/pom.xml
RUN mvn clean
RUN mvn package
VOLUME /app

# Tomcat config
FROM tomcat:9.0.20-jre8 As tomcat-app
COPY --from=maven-app /app/target/Joboonja-1.0-SNAPSHOT.war ./webapps/ROOT.war
RUN rm -r ./webapps/ROOT

CMD ["bin/catalina.sh", "run"]

