# Kata 02

## start.spring.io
To create a new Spring Boot project for Kata 02, you can use the following `curl` command to download the project as a ZIP file. This command specifies the project type, language, platform version, packaging type, JVM version, group ID, artifact ID, name, description, package name, and dependencies.
You can run this command in your terminal to download the project:
```bash
curl -o kata-02.zip \
  "https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.5.3&packaging=jar&jvmVersion=24&groupId=ch.kata&artifactId=kata-02&name=kata-02&description=Kata%2002%20Project&packageName=ch.kata.kata-02&dependencies=web,devtools,lombok,modulith"
```