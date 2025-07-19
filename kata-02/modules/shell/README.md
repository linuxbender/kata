Generate a self-signed certificate and create a keystore for use with Tomcat.

## Generate a Self-Signed Certificate

```bash
    # Private Key and Certificate Generation
    openssl req -x509 -newkey rsa:2048 -keyout key.pem -out cert.pem -days 365 -nodes -subj "/CN=localhost"
    # InPKCS12 Keystore transformation
    openssl pkcs12 -export -in cert.pem -inkey key.pem -out keystore.p12 -name tomcat -password pass:password
    # cp to backend resources
    mv keystore.p12 backend/src/main/resources/
    mv cert.pem backend/src/main/resources/
    mv key.pem backend/src/main/resources/
```