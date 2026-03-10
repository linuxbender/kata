package ch.theforce.kata_04.rule.cashflow;

import ch.theforce.kata_04.dto.CashFlowDto;
import ch.theforce.kata_04.validation.Invalid;
import ch.theforce.kata_04.validation.Valid;
import ch.theforce.kata_04.validation.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the AmountMin validation rule.
 * This test class verifies that the AmountMin rule correctly validates
 * that the amount in a CashFlowDto is greater than 100.
 */
@DisplayName("AmountMin Validation Rule Tests")
class AmountMinTest {

    private AmountMin validationRule;
    private CashFlowDto cashFlowDto;

    /**
     * Setup method to initialize the validation rule and cash flow DTO before each test.
     */
    @BeforeEach
    void setUp() {
        validationRule = new AmountMin();
        cashFlowDto = new CashFlowDto();
    }

    /**
     * Test: Amount is greater than 100.
     * Expected: Validation passes.
     */
    @Test
    @DisplayName("Should pass validation when amount is greater than 100")
    void testValidationPassesWhenAmountIsGreaterThan100() {
        // Arrange
        cashFlowDto.setAmount(new BigDecimal("150.00"));

        // Act
        ValidationResult<CashFlowDto> result = validationRule.validate(cashFlowDto);

        // Assert
        assertThat(result).isInstanceOf(Valid.class);
        assertThat(((Valid<CashFlowDto>) result).data()).isEqualTo(cashFlowDto);
    }

    /**
     * Test: Amount is exactly 100 or less.
     * Expected: Validation fails because amount must be greater than 100.
     */
    @Test
    @DisplayName("Should fail validation when amount is 100 or less")
    void testValidationFailsWhenAmountIs100OrLess() {
        // Arrange - test with exactly 100
        cashFlowDto.setAmount(new BigDecimal("100.00"));

        // Act
        ValidationResult<CashFlowDto> result = validationRule.validate(cashFlowDto);

        // Assert
        assertThat(result).isInstanceOf(Invalid.class);
        Invalid<CashFlowDto> invalid = (Invalid<CashFlowDto>) result;
        assertThat(invalid.errors()).hasSize(1);
        assertThat(invalid.errors().getFirst().field()).isEqualTo("amount");
    }

    /**
     * Test: Amount is significantly less than 100.
     * Expected: Validation fails.
     */
    @Test
    @DisplayName("Should fail validation when amount is significantly less than 100")
    void testValidationFailsWhenAmountIsBelowThreshold() {
        // Arrange
        cashFlowDto.setAmount(new BigDecimal("50.00"));

        // Act
        ValidationResult<CashFlowDto> result = validationRule.validate(cashFlowDto);

        // Assert
        assertThat(result).isInstanceOf(Invalid.class);
    }

    /**
     * Test: Amount is just above 100 (boundary test).
     * Expected: Validation passes because 100.01 is greater than 100.
     */
    @Test
    @DisplayName("Should pass validation when amount is just above 100")
    void testValidationPassesWhenAmountIsJustAboveThreshold() {
        // Arrange
        cashFlowDto.setAmount(new BigDecimal("100.01"));

        // Act
        ValidationResult<CashFlowDto> result = validationRule.validate(cashFlowDto);

        // Assert
        assertThat(result).isInstanceOf(Valid.class);
    }
}


