# Verwende ein offizielles OpenJDK-Image als Basis
FROM openjdk:24-jdk-slim

# Setze das Arbeitsverzeichnis im Container
WORKDIR /app

# Kopiere die JAR-Datei (dies geht davon aus, dass du die JAR mit Maven/Gradle gebaut hast)
COPY target/customer-backend-*-SNAPSHOT.jar customer-backend.jar

# Setze den Befehl, der beim Starten des Containers ausgeführt wird
ENTRYPOINT ["java", "-jar", "customer-backend.jar"]

# Der Port, auf dem das Backend läuft
EXPOSE 8082
