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
 * Unit tests for the AmountMax validation rule.
 * This test class verifies that the AmountMax rule correctly validates
 * that the amount in a CashFlowDto is less than 1000.
 */
@DisplayName("AmountMax Validation Rule Tests")
class AmountMaxTest {

    private AmountMax validationRule;
    private CashFlowDto cashFlowDto;

    /**
     * Setup method to initialize the validation rule and cash flow DTO before each test.
     */
    @BeforeEach
    void setUp() {
        validationRule = new AmountMax();
        cashFlowDto = new CashFlowDto();
    }

    /**
     * Test: Amount is less than 1000.
     * Expected: Validation passes.
     */
    @Test
    @DisplayName("Should pass validation when amount is less than 1000")
    void testValidationPassesWhenAmountIsLessThan1000() {
        // Arrange
        cashFlowDto.setAmount(new BigDecimal("500.00"));

        // Act
        ValidationResult<CashFlowDto> result = validationRule.validate(cashFlowDto);

        // Assert
        assertThat(result).isInstanceOf(Valid.class);
        assertThat(((Valid<CashFlowDto>) result).data()).isEqualTo(cashFlowDto);
    }

    /**
     * Test: Amount is exactly 1000 or greater.
     * Expected: Validation fails because amount must be less than 1000.
     */
    @Test
    @DisplayName("Should fail validation when amount is 1000 or greater")
    void testValidationFailsWhenAmountIs1000OrGreater() {
        // Arrange
        cashFlowDto.setAmount(new BigDecimal("1000.00"));

        // Act
        ValidationResult<CashFlowDto> result = validationRule.validate(cashFlowDto);

        // Assert
        assertThat(result).isInstanceOf(Invalid.class);
        Invalid<CashFlowDto> invalid = (Invalid<CashFlowDto>) result;
        assertThat(invalid.errors()).hasSize(1);
        assertThat(invalid.errors().getFirst().field()).isEqualTo("amount");
    }

    /**
     * Test: Amount is significantly greater than 1000.
     * Expected: Validation fails.
     */
    @Test
    @DisplayName("Should fail validation when amount is significantly greater than 1000")
    void testValidationFailsWhenAmountIsAboveThreshold() {
        // Arrange
        cashFlowDto.setAmount(new BigDecimal("1500.00"));

        // Act
        ValidationResult<CashFlowDto> result = validationRule.validate(cashFlowDto);

        // Assert
        assertThat(result).isInstanceOf(Invalid.class);
    }

    /**
     * Test: Amount is just below 1000 (boundary test).
     * Expected: Validation passes because 999.99 is less than 1000.
     */
    @Test
    @DisplayName("Should pass validation when amount is just below 1000")
    void testValidationPassesWhenAmountIsJustBelowThreshold() {
        // Arrange
        cashFlowDto.setAmount(new BigDecimal("999.99"));


        // Act
        ValidationResult<CashFlowDto> result = validationRule.validate(cashFlowDto);

        // Assert
        assertThat(result).isInstanceOf(Valid.class);
    }
}

