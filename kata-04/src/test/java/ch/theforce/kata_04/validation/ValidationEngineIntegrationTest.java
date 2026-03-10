package ch.theforce.kata_04.validation;

import ch.theforce.kata_04.dto.AddressDto;
import ch.theforce.kata_04.dto.CashFlowDto;
import ch.theforce.kata_04.rule.address.NameNotNull;
import ch.theforce.kata_04.rule.cashflow.AllowedCurrencies;
import ch.theforce.kata_04.rule.cashflow.AmountMax;
import ch.theforce.kata_04.rule.cashflow.AmountMin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the ValidationEngine.
 * This test class verifies how many validation rules are registered and used
 * for different object types (CashFlowDto and AddressDto).
 */
@SpringBootTest
@DisplayName("ValidationEngine Integration Tests")
class ValidationEngineIntegrationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ValidationEngine validationEngine;

    /**
     * Test: Count the number of CashFlow validation rules registered.
     * Expected: AmountMin, AmountMax, and AllowedCurrencies rules should be found.
     */
    @Test
    @DisplayName("Should discover all registered CashFlow validation rules")
    void testDiscoverCashFlowValidationRules() {
        // Arrange
        String[] beanNames = applicationContext.getBeanNamesForType(
                ResolvableType.forClassWithGenerics(ValidationRule.class, CashFlowDto.class)
        );

        // Act & Assert
        assertThat(beanNames).hasSize(3)
                .containsExactlyInAnyOrder(
                        "amountMin",
                        "amountMax",
                        "allowedCurrencies"
                );
    }

    /**
     * Test: Count the number of Address validation rules registered.
     * Expected: NameNotNull rule should be found.
     */
    @Test
    @DisplayName("Should discover all registered Address validation rules")
    void testDiscoverAddressValidationRules() {
        // Arrange
        String[] beanNames = applicationContext.getBeanNamesForType(
                ResolvableType.forClassWithGenerics(ValidationRule.class, AddressDto.class)
        );

        // Act & Assert
        assertThat(beanNames).hasSize(1)
                .containsExactly("nameNotNull");
    }

    /**
     * Test: Execute ValidationEngine with valid CashFlowDto and verify all rules are executed.
     * Expected: All three CashFlow rules should pass validation.
     */
    @Test
    @DisplayName("Should execute all CashFlow rules and pass validation with valid data")
    void testValidationEngineExecutesCashFlowRules() {
        // Arrange
        CashFlowDto validCashFlow = new CashFlowDto();
        validCashFlow.setAmount(new BigDecimal("500.00"));
        validCashFlow.setCurrencyCd("USD");
        validCashFlow.setName("Valid Transaction");

        // Act
        ValidationResult<CashFlowDto> result = validationEngine.runParallel(validCashFlow);

        // Assert
        assertThat(result).isInstanceOf(Valid.class);
        assertThat(((Valid<CashFlowDto>) result).data()).isEqualTo(validCashFlow);
    }

    /**
     * Test: Execute ValidationEngine with invalid CashFlowDto and verify all rules are executed.
     * Expected: All three CashFlow rules should fail validation.
     */
    @Test
    @DisplayName("Should execute all CashFlow rules and collect all validation errors")
    void testValidationEngineExecutesCashFlowRulesWithErrors() {
        // Arrange
        CashFlowDto invalidCashFlow = new CashFlowDto();
        invalidCashFlow.setAmount(new BigDecimal("50.00"));  // Below minimum (100)
        invalidCashFlow.setCurrencyCd("GBP");  // Invalid currency
        invalidCashFlow.setName("Invalid Transaction");

        // Act
        ValidationResult<CashFlowDto> result = validationEngine.runParallel(invalidCashFlow);

        // Assert
        assertThat(result).isInstanceOf(Invalid.class);
        Invalid<CashFlowDto> invalid = (Invalid<CashFlowDto>) result;
        assertThat(invalid.errors()).hasSize(2)
                .extracting("field")
                .containsExactlyInAnyOrder("amount", "currencyCd");
    }

    /**
     * Test: Execute ValidationEngine with valid AddressDto and verify all rules are executed.
     * Expected: The NameNotNull rule should pass validation.
     */
    @Test
    @DisplayName("Should execute all Address rules and pass validation with valid data")
    void testValidationEngineExecutesAddressRules() {
        // Arrange
        AddressDto validAddress = new AddressDto();
        validAddress.setFristName("John");
        validAddress.setLastName("Doe");
        validAddress.setStreet("123 Main St");
        validAddress.setPostalCode("12345");
        validAddress.setCity("Springfield");

        // Act
        ValidationResult<AddressDto> result = validationEngine.runParallel(validAddress);

        // Assert
        assertThat(result).isInstanceOf(Valid.class);
        assertThat(((Valid<AddressDto>) result).data()).isEqualTo(validAddress);
    }

    /**
     * Test: Execute ValidationEngine with invalid AddressDto and verify all rules are executed.
     * Expected: The NameNotNull rule should fail validation with two errors.
     */
    @Test
    @DisplayName("Should execute all Address rules and collect all validation errors")
    void testValidationEngineExecutesAddressRulesWithErrors() {
        // Arrange
        AddressDto invalidAddress = new AddressDto();
        invalidAddress.setFristName(null);  // Null first name
        invalidAddress.setLastName(null);   // Null last name
        invalidAddress.setStreet("123 Main St");
        invalidAddress.setPostalCode("12345");
        invalidAddress.setCity("Springfield");

        // Act
        ValidationResult<AddressDto> result = validationEngine.runParallel(invalidAddress);

        // Assert
        assertThat(result).isInstanceOf(Invalid.class);
        Invalid<AddressDto> invalid = (Invalid<AddressDto>) result;
        assertThat(invalid.errors()).hasSize(2)
                .extracting("field")
                .containsExactlyInAnyOrder("fristName", "lastName");
    }

    /**
     * Test: Verify that ValidationRules are properly instantiated as Spring Beans.
     * Expected: All expected rule classes should be present in the application context.
     */
    @Test
    @DisplayName("Should have all validation rule beans registered in ApplicationContext")
    void testValidationRuleBeansAreRegistered() {
        // Act & Assert - Check if rule beans exist
        assertThat(applicationContext.containsBean("amountMin")).isTrue();
        assertThat(applicationContext.containsBean("amountMax")).isTrue();
        assertThat(applicationContext.containsBean("allowedCurrencies")).isTrue();
        assertThat(applicationContext.containsBean("nameNotNull")).isTrue();
    }

    /**
     * Test: Verify that the correct bean types are registered.
     * Expected: All beans should be instances of ValidationRule interface.
     */
    @Test
    @DisplayName("Should have correct bean types for validation rules")
    void testValidationRuleBeansHaveCorrectTypes() {
        // Act & Assert
        Object amountMinBean = applicationContext.getBean("amountMin");
        Object amountMaxBean = applicationContext.getBean("amountMax");
        Object allowedCurrenciesBean = applicationContext.getBean("allowedCurrencies");
        Object nameNotNullBean = applicationContext.getBean("nameNotNull");

        assertThat(amountMinBean).isInstanceOf(AmountMin.class);
        assertThat(amountMaxBean).isInstanceOf(AmountMax.class);
        assertThat(allowedCurrenciesBean).isInstanceOf(AllowedCurrencies.class);
        assertThat(nameNotNullBean).isInstanceOf(NameNotNull.class);
    }
}

