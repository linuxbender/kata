package ch.kata_01.kata_01.service;

import ch.kata_01.kata_01.configuration.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Service for handling security operations such as generating and validating JWT tokens.
 */
@Service
@Slf4j
public class SecurityService {

    private final SecretKey secretKey;

    public SecurityService(SecurityProperties securityProperties) {

        if (securityProperties.getSecret() == null || securityProperties.getSecret().isEmpty()) {
            log.error("Security secret is not set.");
            throw new IllegalStateException("Security secret is not set.");
        }

        this.secretKey = Keys.hmacShaKeyFor(securityProperties
                .getSecret()
                .getBytes(StandardCharsets.UTF_8));

    }

    /**
     * Generates a JWT token with a subject and expiration time.
     *
     * @return the generated JWT token
     */
    public String generateToken() {
        if (secretKey == null) {
            log.error("Secret key is not initialized.");
            throw new IllegalStateException("Secret key is not initialized.");
        }
        return Jwts.builder()
                .setSubject("user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates the JWT token and returns the subject.
     *
     * @param token the JWT token to validate
     * @return the subject of the token
     */
    public String validateTokenAndGetSubject(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        if (claims == null || claims.getSubject() == null) {
            log.error("Token is invalid");
            throw new IllegalStateException("Token is invalid");
        }
        if (claims.getExpiration().before(new Date())) {
            log.error("Token has expired.");
            throw new IllegalStateException("Token has expired.");
        }
        return claims.getSubject();
    }
}
