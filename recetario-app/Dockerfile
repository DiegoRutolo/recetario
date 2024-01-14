FROM maven:3.8.6 as build
ENV HOME=/usr/recetario-app
RUN mkdir -p ${HOME}
WORKDIR ${HOME}
# COPY .mvn/ ${HOME}/.mvn/
# COPY mvnw ${HOME}/mvnw
COPY src/ ${HOME}/src/
COPY pom.xml ${HOME}
# RUN --mount=type=cache,target=/root/.m2 ./mvnw -f $HOME/pom.xml clean package
RUN mvn -f $HOME/pom.xml clean package

FROM openjdk:11
ARG JAR_FILE=/usr/recetario-app/target/*.jar
COPY --from=build $JAR_FILE /app/runner.jar
EXPOSE 8080
ENTRYPOINT java -jar /app/runner.jar
