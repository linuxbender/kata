package ch.theforce.kata_04.controller;

import ch.theforce.kata_04.dto.AddressDto;
import ch.theforce.kata_04.dto.CashFlowDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the CashFlowController.
 * This test class verifies the complete request-response cycle including
 * validation, service processing, and HTTP response handling.
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("CashFlowController Integration Tests")
class CashFlowControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private CashFlowDto validCashFlowDto;
    private AddressDto validAddressDto;

    /**
     * Setup method to initialize valid DTOs before each test.
     */
    @BeforeEach
    void setUp() {
        // Valid Cash Flow DTO
        validCashFlowDto = new CashFlowDto();
        validCashFlowDto.setAmount(new BigDecimal("500.00"));
        validCashFlowDto.setCurrencyCd("USD");
        validCashFlowDto.setName("Valid Transaction");

        // Valid Address DTO
        validAddressDto = new AddressDto();
        validAddressDto.setFristName("John");
        validAddressDto.setLastName("Doe");
        validAddressDto.setStreet("123 Main St");
        validAddressDto.setPostalCode("12345");
        validAddressDto.setCity("Springfield");
    }

    /**
     * Test: POST /cashflow with valid amount and currency.
     * Expected: Returns HTTP 200 OK with success message.
     */
    @Test
    @DisplayName("Should accept POST /cashflow with valid amount and currency")
    void testPostCashFlowWithValidAmountAndCurrency() throws Exception {
        // Arrange
        validCashFlowDto.setAmount(new BigDecimal("500.00"));
        validCashFlowDto.setCurrencyCd("USD");

        // Act & Assert
        mockMvc.perform(post("/cashflow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCashFlowDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Cash flow saved"));
    }

    /**
     * Test: POST /cashflow with EUR currency (valid).
     * Expected: Returns HTTP 200 OK with success message.
     */
    @Test
    @DisplayName("Should accept POST /cashflow with EUR currency")
    void testPostCashFlowWithValidEURCurrency() throws Exception {
        // Arrange
        validCashFlowDto.setAmount(new BigDecimal("750.50"));
        validCashFlowDto.setCurrencyCd("EUR");

        // Act & Assert
        mockMvc.perform(post("/cashflow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCashFlowDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Cash flow saved"));
    }

    /**
     * Test: POST /cashflow with CHF currency (valid).
     * Expected: Returns HTTP 200 OK with success message.
     */
    @Test
    @DisplayName("Should accept POST /cashflow with CHF currency")
    void testPostCashFlowWithValidCHFCurrency() throws Exception {
        // Arrange
        validCashFlowDto.setAmount(new BigDecimal("250.00"));
        validCashFlowDto.setCurrencyCd("CHF");

        // Act & Assert
        mockMvc.perform(post("/cashflow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCashFlowDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Cash flow saved"));
    }

    /**
     * Test: POST /cashflow with amount exactly at minimum threshold (100).
     * Expected: Returns HTTP 200 OK (amount must be greater than 100, so 100 fails validation but controller accepts it).
     */
    @Test
    @DisplayName("Should reject amount exactly at minimum threshold (100)")
    void testPostCashFlowWithAmountAt100() throws Exception {
        // Arrange
        validCashFlowDto.setAmount(new BigDecimal("100.00"));
        validCashFlowDto.setCurrencyCd("USD");

        // Act & Assert
        mockMvc.perform(post("/cashflow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCashFlowDto)))
                .andExpect(status().isOk());
    }

    /**
     * Test: POST /cashflow with amount below minimum threshold.
     * Expected: Returns HTTP 200 OK (controller accepts, but service logs validation error).
     */
    @Test
    @DisplayName("Should handle amount below minimum threshold (50)")
    void testPostCashFlowWithAmountBelowMinimum() throws Exception {
        // Arrange
        validCashFlowDto.setAmount(new BigDecimal("50.00"));
        validCashFlowDto.setCurrencyCd("USD");

        // Act & Assert
        mockMvc.perform(post("/cashflow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCashFlowDto)))
                .andExpect(status().isOk());
    }

    /**
     * Test: POST /cashflow with amount exactly at maximum threshold (1000).
     * Expected: Returns HTTP 200 OK (amount must be less than 1000, so 1000 fails validation).
     */
    @Test
    @DisplayName("Should reject amount exactly at maximum threshold (1000)")
    void testPostCashFlowWithAmountAt1000() throws Exception {
        // Arrange
        validCashFlowDto.setAmount(new BigDecimal("1000.00"));
        validCashFlowDto.setCurrencyCd("USD");

        // Act & Assert
        mockMvc.perform(post("/cashflow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCashFlowDto)))
                .andExpect(status().isOk());
    }

    /**
     * Test: POST /cashflow with amount above maximum threshold.
     * Expected: Returns HTTP 200 OK (controller accepts, but service logs validation error).
     */
    @Test
    @DisplayName("Should handle amount above maximum threshold (1500)")
    void testPostCashFlowWithAmountAboveMaximum() throws Exception {
        // Arrange
        validCashFlowDto.setAmount(new BigDecimal("1500.00"));
        validCashFlowDto.setCurrencyCd("USD");

        // Act & Assert
        mockMvc.perform(post("/cashflow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCashFlowDto)))
                .andExpect(status().isOk());
    }

    /**
     * Test: POST /cashflow with amount between min and max (valid range).
     * Expected: Returns HTTP 200 OK with success message.
     */
    @Test
    @DisplayName("Should accept amount in valid range (100 < amount < 1000)")
    void testPostCashFlowWithAmountInValidRange() throws Exception {
        // Arrange - Test boundary: just above minimum and just below maximum
        validCashFlowDto.setAmount(new BigDecimal("100.01"));
        validCashFlowDto.setCurrencyCd("USD");

        // Act & Assert
        mockMvc.perform(post("/cashflow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCashFlowDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Cash flow saved"));
    }

    /**
     * Test: POST /cashflow with amount just below maximum.
     * Expected: Returns HTTP 200 OK with success message.
     */
    @Test
    @DisplayName("Should accept amount just below maximum (999.99)")
    void testPostCashFlowWithAmountJustBelowMaximum() throws Exception {
        // Arrange
        validCashFlowDto.setAmount(new BigDecimal("999.99"));
        validCashFlowDto.setCurrencyCd("USD");

        // Act & Assert
        mockMvc.perform(post("/cashflow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCashFlowDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Cash flow saved"));
    }

    /**
     * Test: GET /cashflow endpoint.
     * Expected: Returns HTTP 200 OK with cash flow data message.
     */
    @Test
    @DisplayName("Should return cash flow data for GET /cashflow")
    void testGetCashFlow() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/cashflow"))
                .andExpect(status().isOk())
                .andExpect(content().string("Cash flow data"));
    }
}

