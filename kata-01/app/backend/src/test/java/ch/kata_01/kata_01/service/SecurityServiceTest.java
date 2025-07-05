package ch.kata_01.kata_01.service;

import ch.kata_01.kata_01.configuration.SecurityProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecurityServiceTest {

    private SecurityService securityService;

    @BeforeEach
    void setUp() {
        SecurityProperties properties = new SecurityProperties();
        properties.setSecret("my-super-secret-key-which-is-long-enough");
        securityService = new SecurityService(properties);
    }

    @Test
    void generateToken_shouldReturnValidJwt() {
        String token = securityService.generateToken();
        assertNotNull(token);
    }

    @Test
    void validateTokenAndGetSubject_shouldReturnCorrectSubject() {
        String token = securityService.generateToken();
        String subject = securityService.validateTokenAndGetSubject(token);
        assertEquals("user", subject);
    }

    @Test
    void constructor_shouldThrowExceptionIfSecretIsNull() {
        SecurityProperties properties = new SecurityProperties();
        properties.setSecret(null);
        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                new SecurityService(properties)
        );
        assertEquals("Security secret is not set.", ex.getMessage());
    }

    @Test
    void constructor_shouldThrowExceptionIfSecretIsEmpty() {
        SecurityProperties properties = new SecurityProperties();
        properties.setSecret("");
        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                new SecurityService(properties)
        );
        assertEquals("Security secret is not set.", ex.getMessage());
    }
}
