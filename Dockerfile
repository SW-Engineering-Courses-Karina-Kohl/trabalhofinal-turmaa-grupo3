# compilação
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# tomcat
FROM tomcat:10.1-jdk17-temurin
WORKDIR /usr/local/tomcat/webapps/

# remove apps padrão para evitar conflito na raiz
RUN rm -rf /usr/local/tomcat/webapps/*

# pom.xml define <finalName>ROOT</finalName>
COPY --from=build /app/target/ROOT.war ./ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
