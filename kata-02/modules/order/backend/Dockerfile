# Verwende ein offizielles OpenJDK-Image als Basis
FROM openjdk:24-jdk-slim

# Setze das Arbeitsverzeichnis im Container
WORKDIR /app

# Kopiere die JAR-Datei (dies geht davon aus, dass du die JAR mit Maven/Gradle gebaut hast)
COPY target/order-backend-*-SNAPSHOT.jar order-backend.jar

# Setze den Befehl, der beim Starten des Containers ausgeführt wird
ENTRYPOINT ["java", "-jar", "order-backend.jar"]

# Der Port, auf dem das Backend läuft
EXPOSE 8081
