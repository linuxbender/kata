package ch.theforce.kata_04.rule.cashflow;

import ch.theforce.kata_04.dto.CashFlowDto;
import ch.theforce.kata_04.validation.Invalid;
import ch.theforce.kata_04.validation.Valid;
import ch.theforce.kata_04.validation.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the AllowedCurrencies validation rule.
 * This test class verifies that the AllowedCurrencies rule correctly validates
 * that the currency code in a CashFlowDto is one of the allowed currencies (USD, EUR, CHF).
 */
@DisplayName("AllowedCurrencies Validation Rule Tests")
class AllowedCurrenciesTest {

    private AllowedCurrencies validationRule;
    private CashFlowDto cashFlowDto;

    /**
     * Setup method to initialize the validation rule and cash flow DTO before each test.
     */
    @BeforeEach
    void setUp() {
        validationRule = new AllowedCurrencies();
        cashFlowDto = new CashFlowDto();
    }

    /**
     * Test: Currency is one of the allowed currencies (USD, EUR, CHF).
     * Expected: Validation passes.
     */
    @Test
    @DisplayName("Should pass validation when currency is allowed (USD)")
    void testValidationPassesWhenCurrencyIsAllowed() {
        // Arrange
        cashFlowDto.setCurrencyCd("USD");

        // Act
        ValidationResult<CashFlowDto> result = validationRule.validate(cashFlowDto);

        // Assert
        assertThat(result).isInstanceOf(Valid.class);
        assertThat(((Valid<CashFlowDto>) result).data()).isEqualTo(cashFlowDto);
    }

    /**
     * Test: Currency is case-insensitive (lowercase usd).
     * Expected: Validation passes because currency is converted to uppercase.
     */
    @Test
    @DisplayName("Should pass validation when currency is lowercase")
    void testValidationPassesWhenCurrencyIsLowercase() {
        // Arrange
        cashFlowDto.setCurrencyCd("eur");

        // Act
        ValidationResult<CashFlowDto> result = validationRule.validate(cashFlowDto);

        // Assert
        assertThat(result).isInstanceOf(Valid.class);
    }

    /**
     * Test: Currency is not in allowed list.
     * Expected: Validation fails with error message.
     */
    @Test
    @DisplayName("Should fail validation when currency is not allowed")
    void testValidationFailsWhenCurrencyIsNotAllowed() {
        // Arrange
        cashFlowDto.setCurrencyCd("GBP");

        // Act
        ValidationResult<CashFlowDto> result = validationRule.validate(cashFlowDto);

        // Assert
        assertThat(result).isInstanceOf(Invalid.class);
        Invalid<CashFlowDto> invalid = (Invalid<CashFlowDto>) result;
        assertThat(invalid.errors()).hasSize(1);
        assertThat(invalid.errors().getFirst().field()).isEqualTo("currencyCd");
    }

    /**
     * Test: Currency is empty string.
     * Expected: Validation fails.
     */
    @Test
    @DisplayName("Should fail validation when currency is empty string")
    void testValidationFailsWhenCurrencyIsEmpty() {
        // Arrange
        cashFlowDto.setCurrencyCd("");

        // Act
        ValidationResult<CashFlowDto> result = validationRule.validate(cashFlowDto);

        // Assert
        assertThat(result).isInstanceOf(Invalid.class);
    }

    /**
     * Test: Currency is CHF (third allowed currency).
     * Expected: Validation passes.
     */
    @Test
    @DisplayName("Should pass validation when currency is CHF")
    void testValidationPassesWhenCurrencyIsCHF() {
        // Arrange
        cashFlowDto.setCurrencyCd("CHF");

        // Act
        ValidationResult<CashFlowDto> result = validationRule.validate(cashFlowDto);

        // Assert
        assertThat(result).isInstanceOf(Valid.class);
    }
}

