FROM openjdk:11
COPY target/recetario-0.0.1-SNAPSHOT.jar recetario-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "java", "-jar", "recetario-0.0.1-SNAPSHOT.jar" ]