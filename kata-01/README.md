# Kata 01

## Technologies Used

React, TypeScript, Vite, SpringBoot, SSL, SecureCookies, JWT

## Description

Generate a self-signed certificate and create a keystore for use with Tomcat.

## Generate a Self-Signed Certificate

```bash
    # Private Key and Certificate Generation
    openssl req -x509 -newkey rsa:2048 -keyout key.pem -out cert.pem -days 365 -nodes -subj "/CN=localhost"
    # InPKCS12 Keystore transformation
    openssl pkcs12 -export -in cert.pem -inkey key.pem -out keystore.p12 -name tomcat -password pass:password
    # cp to backend resources
    mv keystore.p12 app/backend/src/main/resources/
    mv cert.pem app/backend/src/main/resources/
    mv key.pem app/backend/src/main/resources/
```

## Setup in Spring Boot with application.yml

```yaml
server:
  port: 8443
  ssl:
    key-store: classpath:keystore.p12
    key-store-password: password
    key-store-type: PKCS12
    key-alias: tomcat
```
## Initialize Spring Boot Project

```bash
    # https://start.spring.io/#!type=gradle-project&language=java&platformVersion=3.5.3&packaging=jar&jvmVersion=24&groupId=ch.kata-01&artifactId=kata-01&name=kata-01&description=Kata%2001%20spring%20boot%20backend&packageName=ch.kata-01.kata-01&dependencies=lombok,devtools,web,h2
    curl https://start.spring.io/starter.tgz -d dependencies=web,security -d baseDir=kata-01 | tar -xzvf -
    cd kata-01
```

## Run Spring Boot Application

### Environment variables for local development
```bash
    export SECRET_VAULT_TOKEN=Use_a_Vault_or_GitActions_secret_this_is_not_secure
```

### Run the application

```bash
    ./mvnw spring-boot:run
```
### local address

```bash
    # Open in browser
    open https://localhost:8443/api/v1
```




