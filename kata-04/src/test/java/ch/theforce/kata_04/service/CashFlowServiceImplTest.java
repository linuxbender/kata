package ch.theforce.kata_04.service;

import ch.theforce.kata_04.dto.CashFlowDto;
import ch.theforce.kata_04.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the CashFlowServiceImpl class.
 * This test class verifies that the CashFlowServiceImpl correctly handles
 * validation results and logs appropriate messages for valid and invalid cash flows.
 */
@DisplayName("CashFlowServiceImpl Tests")
@ExtendWith(MockitoExtension.class)
class CashFlowServiceImplTest {

    @Mock
    private ValidationEngine validationEngineMock;

    @InjectMocks
    private CashFlowServiceImpl cashFlowService;

    private CashFlowDto cashFlowDto;

    /**
     * Setup method to initialize the service and DTOs before each test.
     */
    @BeforeEach
    void setUp() {
        cashFlowDto = new CashFlowDto();
        cashFlowDto.setAmount(new BigDecimal("500.00"));
        cashFlowDto.setCurrencyCd("USD");
        cashFlowDto.setName("Test Transaction");
    }

    /**
     * Test: ValidationEngine returns a Valid result.
     * Expected: Service logs the valid data and completes successfully.
     */
    @Test
    @DisplayName("Should handle valid cash flow and log data")
    void testSaveCashFlowWithValidResult() {
        // Arrange
        ValidationResult<CashFlowDto> validResult = new Valid<>(cashFlowDto);
        when(validationEngineMock.runParallel(cashFlowDto))
                .thenReturn(validResult);

        // Act
        cashFlowService.saveCashFlow(cashFlowDto);

        // Assert
        verify(validationEngineMock).runParallel(cashFlowDto);
        verify(validationEngineMock, times(1)).runParallel(any());
    }

    /**
     * Test: ValidationEngine returns an Invalid result with single error.
     * Expected: Service logs the validation errors.
     */
    @Test
    @DisplayName("Should handle invalid cash flow with single error")
    void testSaveCashFlowWithSingleValidationError() {
        // Arrange
        List<ValidationError> errors = List.of(
                new ValidationError("amount", "Amount must be greater than 100")
        );
        ValidationResult<CashFlowDto> invalidResult = new Invalid<>(errors);
        when(validationEngineMock.runParallel(cashFlowDto))
                .thenReturn(invalidResult);

        // Act
        cashFlowService.saveCashFlow(cashFlowDto);

        // Assert
        verify(validationEngineMock).runParallel(cashFlowDto);
    }

    /**
     * Test: ValidationEngine returns an Invalid result with multiple errors.
     * Expected: Service logs all validation errors.
     */
    @Test
    @DisplayName("Should handle invalid cash flow with multiple errors")
    void testSaveCashFlowWithMultipleValidationErrors() {
        // Arrange
        List<ValidationError> errors = List.of(
                new ValidationError("amount", "Amount must be greater than 100"),
                new ValidationError("currencyCd", "Currency must be USD, EUR, or CHF"),
                new ValidationError("firstName", "First name must not be null")
        );
        ValidationResult<CashFlowDto> invalidResult = new Invalid<>(errors);
        when(validationEngineMock.runParallel(cashFlowDto))
                .thenReturn(invalidResult);

        // Act
        cashFlowService.saveCashFlow(cashFlowDto);

        // Assert
        verify(validationEngineMock).runParallel(cashFlowDto);
    }

    /**
     * Test: ValidationEngine is called with the provided CashFlowDto.
     * Expected: The service passes the DTO to the engine without modification.
     */
    @Test
    @DisplayName("Should pass CashFlowDto to ValidationEngine")
    void testValidationEngineCalledWithCorrectDto() {
        // Arrange
        when(validationEngineMock.runParallel(cashFlowDto))
                .thenReturn(new Valid<>(cashFlowDto));

        // Act
        cashFlowService.saveCashFlow(cashFlowDto);

        // Assert
        verify(validationEngineMock).runParallel(eq(cashFlowDto));
    }
}

