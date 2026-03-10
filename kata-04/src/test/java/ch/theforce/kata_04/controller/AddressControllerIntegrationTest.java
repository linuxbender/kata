package ch.theforce.kata_04.controller;

import ch.theforce.kata_04.dto.AddressDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the AddressController.
 * This test class verifies the complete request-response cycle including
 * validation, service processing, and HTTP response handling for address operations.
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("AddressController Integration Tests")
class AddressControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private AddressDto validAddressDto;

    /**
     * Setup method to initialize valid DTOs before each test.
     */
    @BeforeEach
    void setUp() {
        // Valid Address DTO
        validAddressDto = new AddressDto();
        validAddressDto.setFristName("John");
        validAddressDto.setLastName("Doe");
        validAddressDto.setStreet("123 Main St");
        validAddressDto.setPostalCode("12345");
        validAddressDto.setCity("Springfield");
    }

    /**
     * Test: POST /address with valid first name and last name.
     * Expected: Returns HTTP 200 OK with success message.
     */
    @Test
    @DisplayName("Should accept POST /address with valid first and last name")
    void testPostAddressWithValidNames() throws Exception {
        // Arrange
        validAddressDto.setFristName("John");
        validAddressDto.setLastName("Doe");

        // Act & Assert
        mockMvc.perform(post("/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validAddressDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Address saved"));
    }

    /**
     * Test: POST /address with special characters in names.
     * Expected: Returns HTTP 200 OK with success message.
     */
    @Test
    @DisplayName("Should accept POST /address with special characters in names")
    void testPostAddressWithSpecialCharacters() throws Exception {
        // Arrange
        validAddressDto.setFristName("Jean-Pierre");
        validAddressDto.setLastName("O'Connor");

        // Act & Assert
        mockMvc.perform(post("/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validAddressDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Address saved"));
    }

    /**
     * Test: POST /address with unicode characters in names.
     * Expected: Returns HTTP 200 OK with success message.
     */
    @Test
    @DisplayName("Should accept POST /address with unicode characters in names")
    void testPostAddressWithUnicodeCharacters() throws Exception {
        // Arrange
        validAddressDto.setFristName("Jörg");
        validAddressDto.setLastName("Müller");

        // Act & Assert
        mockMvc.perform(post("/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validAddressDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Address saved"));
    }

    /**
     * Test: POST /address with first name as null.
     * Expected: Returns HTTP 200 OK (controller accepts, but service logs validation error).
     */
    @Test
    @DisplayName("Should handle POST /address with null first name")
    void testPostAddressWithNullFirstName() throws Exception {
        // Arrange
        validAddressDto.setFristName(null);
        validAddressDto.setLastName("Doe");

        // Act & Assert
        mockMvc.perform(post("/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validAddressDto)))
                .andExpect(status().isOk());
    }

    /**
     * Test: POST /address with last name as null.
     * Expected: Returns HTTP 200 OK (controller accepts, but service logs validation error).
     */
    @Test
    @DisplayName("Should handle POST /address with null last name")
    void testPostAddressWithNullLastName() throws Exception {
        // Arrange
        validAddressDto.setFristName("John");
        validAddressDto.setLastName(null);

        // Act & Assert
        mockMvc.perform(post("/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validAddressDto)))
                .andExpect(status().isOk());
    }

    /**
     * Test: POST /address with both first name and last name as null.
     * Expected: Returns HTTP 200 OK (controller accepts, but service logs two validation errors).
     */
    @Test
    @DisplayName("Should handle POST /address with both names null")
    void testPostAddressWithBothNamesNull() throws Exception {
        // Arrange
        validAddressDto.setFristName(null);
        validAddressDto.setLastName(null);

        // Act & Assert
        mockMvc.perform(post("/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validAddressDto)))
                .andExpect(status().isOk());
    }

    /**
     * Test: POST /address with empty string names (valid, not null).
     * Expected: Returns HTTP 200 OK with success message.
     */
    @Test
    @DisplayName("Should accept POST /address with empty string names")
    void testPostAddressWithEmptyStringNames() throws Exception {
        // Arrange
        validAddressDto.setFristName("");
        validAddressDto.setLastName("");

        // Act & Assert
        mockMvc.perform(post("/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validAddressDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Address saved"));
    }

    /**
     * Test: POST /address with whitespace names (valid, not null).
     * Expected: Returns HTTP 200 OK with success message.
     */
    @Test
    @DisplayName("Should accept POST /address with whitespace names")
    void testPostAddressWithWhitespaceNames() throws Exception {
        // Arrange
        validAddressDto.setFristName("   ");
        validAddressDto.setLastName("   ");

        // Act & Assert
        mockMvc.perform(post("/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validAddressDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Address saved"));
    }

    /**
     * Test: POST /address with complete address information.
     * Expected: Returns HTTP 200 OK with success message.
     */
    @Test
    @DisplayName("Should accept POST /address with complete address information")
    void testPostAddressWithCompleteInformation() throws Exception {
        // Arrange
        validAddressDto.setFristName("John");
        validAddressDto.setLastName("Doe");
        validAddressDto.setStreet("123 Main St");
        validAddressDto.setPostalCode("12345");
        validAddressDto.setCity("Springfield");

        // Act & Assert
        mockMvc.perform(post("/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validAddressDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Address saved"));
    }

    /**
     * Test: POST /address with long names.
     * Expected: Returns HTTP 200 OK with success message.
     */
    @Test
    @DisplayName("Should accept POST /address with long names")
    void testPostAddressWithLongNames() throws Exception {
        // Arrange
        validAddressDto.setFristName("Christopher Alexander");
        validAddressDto.setLastName("von Humboldt-Albertus");

        // Act & Assert
        mockMvc.perform(post("/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validAddressDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Address saved"));
    }
}

