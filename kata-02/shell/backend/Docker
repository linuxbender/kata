# Verwende ein offizielles OpenJDK-Image als Basis
FROM openjdk:24-jdk-slim

# Setze das Arbeitsverzeichnis im Container
WORKDIR /app

# Kopiere die JAR-Datei (dies geht davon aus, dass du die JAR mit Maven/Gradle gebaut hast)
COPY target/shell-backend-*-SNAPSHOT.jar shell-backend.jar

# Setze den Befehl, der beim Starten des Containers ausgeführt wird
ENTRYPOINT ["java", "-jar", "shell-backend.jar"]

# Der Port, auf dem die Shell Backend Anwendung läuft
EXPOSE 8080
