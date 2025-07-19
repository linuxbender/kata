# Verwende ein offizielles Node.js-Image als Basis
FROM node:24-slim

# Setze das Arbeitsverzeichnis im Container
WORKDIR /app

# Kopiere die package.json und package-lock.json, um die Abhängigkeiten zu installieren
COPY package.json package-lock.json ./

# Installiere die Abhängigkeiten
RUN npm install

# Kopiere den Rest des Frontend-Codes
COPY . .

# Baue die Anwendung für die Produktion
RUN npm run build

# Setze den Befehl, der beim Starten des Containers ausgeführt wird
CMD ["npm", "run", "dev"]

# Der Port, auf dem das Frontend läuft
EXPOSE 3001
