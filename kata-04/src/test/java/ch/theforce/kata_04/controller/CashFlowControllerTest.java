package ch.theforce.kata_04.controller;

import ch.theforce.kata_04.dto.CashFlowDto;
import ch.theforce.kata_04.service.CashFlowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for the CashFlowController class.
 * This test class verifies that the controller correctly handles HTTP requests
 * for cash flow operations and delegates to the service layer.
 */
@DisplayName("CashFlowController Tests")
@ExtendWith(MockitoExtension.class)
class CashFlowControllerTest {

    @Mock
    private CashFlowService cashFlowServiceMock;

    @InjectMocks
    private CashFlowController cashFlowController;

    private CashFlowDto cashFlowDto;

    /**
     * Setup method to initialize the controller and DTOs before each test.
     */
    @BeforeEach
    void setUp() {
        cashFlowDto = new CashFlowDto();
        cashFlowDto.setAmount(new BigDecimal("500.00"));
        cashFlowDto.setCurrencyCd("USD");
        cashFlowDto.setName("Test Transaction");
    }

    /**
     * Test: GET /cashflow endpoint is called.
     * Expected: Returns OK status with cash flow data message.
     */
    @Test
    @DisplayName("Should return OK status for GET /cashflow")
    void testGetCashFlowReturnsOkStatus() {
        // Act
        ResponseEntity<String> response = cashFlowController.getCashFlow();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Cash flow data");
    }

    /**
     * Test: POST /cashflow endpoint is called with valid CashFlowDto.
     * Expected: Service is called and OK status is returned.
     */
    @Test
    @DisplayName("Should save cash flow and return OK status for POST /cashflow")
    void testPostCashFlowCallsServiceAndReturnsOkStatus() {
        // Act
        ResponseEntity<String> response = cashFlowController.postCashFlow(cashFlowDto);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Cash flow saved");
        verify(cashFlowServiceMock).saveCashFlow(cashFlowDto);
    }

    /**
     * Test: POST /cashflow endpoint delegates to service layer.
     * Expected: Service receives the exact CashFlowDto passed to controller.
     */
    @Test
    @DisplayName("Should pass CashFlowDto to service layer")
    void testPostCashFlowPassesDtoToService() {
        // Act
        cashFlowController.postCashFlow(cashFlowDto);

        // Assert
        verify(cashFlowServiceMock).saveCashFlow(cashFlowDto);
    }
}

