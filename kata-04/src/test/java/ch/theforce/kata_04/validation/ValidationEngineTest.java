package ch.theforce.kata_04.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the ValidationEngine class.
 * This test class verifies that the ValidationEngine correctly discovers
 * and executes validation rules in parallel, collecting errors from all rules
 * and returning consolidated validation results.
 */
@DisplayName("ValidationEngine Tests")
class ValidationEngineTest {

    private ValidationEngine validationEngine;
    private ApplicationContext applicationContextMock;

    /**
     * Setup method to initialize the validation engine and mocks before each test.
     */
    @BeforeEach
    void setUp() {
        applicationContextMock = mock(ApplicationContext.class);
        validationEngine = new ValidationEngine(applicationContextMock);
    }

    /**
     * Test: No validation rules are registered for the target type.
     * Expected: Validation passes and returns a Valid result with the target.
     */
    @Test
    @DisplayName("Should return Valid result when no validation rules are registered")
    void testValidationPassesWhenNoRulesRegistered() {
        // Arrange
        String target = "test";
        String[] emptyBeanNames = new String[0];
        when(applicationContextMock.getBeanNamesForType(any(ResolvableType.class)))
                .thenReturn(emptyBeanNames);

        // Act
        ValidationResult<String> result = validationEngine.runParallel(target);

        // Assert
        assertThat(result).isInstanceOf(Valid.class);
        assertThat(((Valid<String>) result).data()).isEqualTo(target);
    }

    /**
     * Test: Single validation rule is registered and passes.
     * Expected: Validation passes and returns a Valid result.
     */
    @Test
    @DisplayName("Should return Valid result when rule passes")
    void testValidationPassesWhenRulePasses() {
        // Arrange
        String target = "test";
        String[] beanNames = {"rule1"};
        ValidationRule<String> passingRule = ValidationResult::ok;

        when(applicationContextMock.getBeanNamesForType(any(ResolvableType.class)))
                .thenReturn(beanNames);
        when(applicationContextMock.getBean("rule1")).thenReturn(passingRule);

        // Act
        ValidationResult<String> result = validationEngine.runParallel(target);

        // Assert
        assertThat(result).isInstanceOf(Valid.class);
    }

    /**
     * Test: Multiple validation rules fail with multiple errors each.
     * Expected: Validation fails and returns an Invalid result with all errors collected.
     */
    @Test
    @DisplayName("Should collect all errors from multiple failing rules")
    void testValidationCollectsErrorsFromMultipleFailingRules() {
        // Arrange
        String target = "test";
        String[] beanNames = {"rule1", "rule2"};
        ValidationRule<String> failingRule1 = t ->
                new Invalid<>(List.of(
                        new ValidationError("field1", "Error 1"),
                        new ValidationError("field2", "Error 2")
                ));
        ValidationRule<String> failingRule2 = t ->
                new Invalid<>(List.of(
                        new ValidationError("field3", "Error 3")
                ));

        when(applicationContextMock.getBeanNamesForType(any(ResolvableType.class)))
                .thenReturn(beanNames);
        when(applicationContextMock.getBean("rule1")).thenReturn(failingRule1);
        when(applicationContextMock.getBean("rule2")).thenReturn(failingRule2);

        // Act
        ValidationResult<String> result = validationEngine.runParallel(target);

        // Assert
        assertThat(result).isInstanceOf(Invalid.class);
        Invalid<String> invalid = (Invalid<String>) result;
        assertThat(invalid.errors()).hasSize(3);
        assertThat(invalid.errors())
                .extracting("field")
                .containsExactlyInAnyOrder("field1", "field2", "field3");
    }

    /**
     * Test: Mix of passing, failing, and exception-throwing rules.
     * Expected: All errors and system errors are collected and returned.
     */
    @Test
    @DisplayName("Should handle mix of passing, failing, and exception-throwing rules")
    void testValidationHandlesMixOfRuleResults() {
        // Arrange
        String target = "test";
        String[] beanNames = {"rule1", "rule2", "rule3"};
        ValidationRule<String> passingRule = ValidationResult::ok;
        ValidationRule<String> failingRule = t ->
                new Invalid<>(List.of(new ValidationError("field", "Error")));
        ValidationRule<String> exceptionRule = t -> {
            throw new RuntimeException("Test exception");
        };

        when(applicationContextMock.getBeanNamesForType(any(ResolvableType.class)))
                .thenReturn(beanNames);
        when(applicationContextMock.getBean("rule1")).thenReturn(passingRule);
        when(applicationContextMock.getBean("rule2")).thenReturn(failingRule);
        when(applicationContextMock.getBean("rule3")).thenReturn(exceptionRule);

        // Act
        ValidationResult<String> result = validationEngine.runParallel(target);

        // Assert
        assertThat(result).isInstanceOf(Invalid.class);
        Invalid<String> invalid = (Invalid<String>) result;
        assertThat(invalid.errors()).hasSize(2);
        assertThat(invalid.errors())
                .extracting("field")
                .containsExactlyInAnyOrder("field", "system");
        assertThat(invalid.errors())
                .anyMatch(error -> error.field().equals("system")
                        && error.message().contains("Unexpected error"));
    }

    /**
     * Test: Multiple rules throw exceptions during execution.
     * Expected: All exceptions are caught and system errors are added with proper messages.
     */
    @Test
    @DisplayName("Should handle rule exceptions and add system errors")
    void testValidationHandlesRuleExceptions() {
        // Arrange
        String target = "test";
        String[] beanNames = {"rule1", "rule2"};
        ValidationRule<String> exceptionRule1 = t -> {
            throw new RuntimeException("Exception 1");
        };
        ValidationRule<String> exceptionRule2 = t -> {
            throw new RuntimeException("Exception 2");
        };

        when(applicationContextMock.getBeanNamesForType(any(ResolvableType.class)))
                .thenReturn(beanNames);
        when(applicationContextMock.getBean("rule1")).thenReturn(exceptionRule1);
        when(applicationContextMock.getBean("rule2")).thenReturn(exceptionRule2);

        // Act
        ValidationResult<String> result = validationEngine.runParallel(target);

        // Assert
        assertThat(result).isInstanceOf(Invalid.class);
        Invalid<String> invalid = (Invalid<String>) result;
        assertThat(invalid.errors()).hasSize(2);
        assertThat(invalid.errors())
                .allMatch(error -> error.field().equals("system")
                        && error.message().contains("Unexpected error"));
    }
}

