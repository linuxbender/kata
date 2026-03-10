package ch.theforce.kata_04.rule.address;

import ch.theforce.kata_04.dto.AddressDto;
import ch.theforce.kata_04.validation.Invalid;
import ch.theforce.kata_04.validation.Valid;
import ch.theforce.kata_04.validation.ValidationError;
import ch.theforce.kata_04.validation.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the NameNotNull validation rule.
 * This test class verifies that the NameNotNull rule correctly validates
 * that both first name and last name fields are not null in an AddressDto.
 */
@DisplayName("NameNotNull Validation Rule Tests")
class NameNotNullTest {

    private NameNotNull validationRule;
    private AddressDto addressDto;

    /**
     * Setup method to initialize the validation rule and address DTO before each test.
     */
    @BeforeEach
    void setUp() {
        validationRule = new NameNotNull();
        addressDto = new AddressDto();
    }

    /**
     * Test: Both first name and last name are provided.
     * Expected: Validation passes and returns a Valid result.
     */
    @Test
    @DisplayName("Should pass validation when both first name and last name are provided")
    void testValidationPassesWhenBothNamesProvided() {
        // Arrange
        addressDto.setFristName("John");
        addressDto.setLastName("Doe");

        // Act
        ValidationResult<AddressDto> result = validationRule.validate(addressDto);

        // Assert
        assertThat(result).isInstanceOf(Valid.class);
        assertThat(((Valid<AddressDto>) result).data()).isEqualTo(addressDto);
    }

    /**
     * Test: Both first name and last name are null.
     * Expected: Validation fails and returns an Invalid result with two errors.
     */
    @Test
    @DisplayName("Should fail validation when both first name and last name are null")
    void testValidationFailsWhenBothNamesAreNull() {
        // Arrange
        addressDto.setFristName(null);
        addressDto.setLastName(null);

        // Act
        ValidationResult<AddressDto> result = validationRule.validate(addressDto);

        // Assert
        assertThat(result).isInstanceOf(Invalid.class);
        Invalid<AddressDto> invalid = (Invalid<AddressDto>) result;
        assertThat(invalid.errors()).hasSize(2);
        assertThat(invalid.errors())
                .containsExactlyInAnyOrder(
                        new ValidationError("fristName", "First name must not be null"),
                        new ValidationError("lastName", "Last name must not be null")
                );
    }

    /**
     * Test: Only first name is null.
     * Expected: Validation fails with one error for first name.
     */
    @Test
    @DisplayName("Should fail validation when only first name is null")
    void testValidationFailsWhenOnlyFirstNameIsNull() {
        // Arrange
        addressDto.setFristName(null);
        addressDto.setLastName("Doe");

        // Act
        ValidationResult<AddressDto> result = validationRule.validate(addressDto);

        // Assert
        assertThat(result).isInstanceOf(Invalid.class);
        Invalid<AddressDto> invalid = (Invalid<AddressDto>) result;
        assertThat(invalid.errors()).hasSize(1);
        assertThat(invalid.errors().getFirst())
                .isEqualTo(new ValidationError("fristName", "First name must not be null"));
    }

    /**
     * Test: Only last name is null.
     * Expected: Validation fails with one error for last name.
     */
    @Test
    @DisplayName("Should fail validation when only last name is null")
    void testValidationFailsWhenOnlyLastNameIsNull() {
        // Arrange
        addressDto.setFristName("John");
        addressDto.setLastName(null);

        // Act
        ValidationResult<AddressDto> result = validationRule.validate(addressDto);

        // Assert
        assertThat(result).isInstanceOf(Invalid.class);
        Invalid<AddressDto> invalid = (Invalid<AddressDto>) result;
        assertThat(invalid.errors()).hasSize(1);
        assertThat(invalid.errors().getFirst())
                .isEqualTo(new ValidationError("lastName", "Last name must not be null"));
    }

    /**
     * Test: Names contain empty strings (which are valid, not null).
     * Expected: Validation passes.
     */
    @Test
    @DisplayName("Should pass validation when names are empty strings")
    void testValidationPassesWhenNamesAreEmpty() {
        // Arrange
        addressDto.setFristName("");
        addressDto.setLastName("");


        // Act
        ValidationResult<AddressDto> result = validationRule.validate(addressDto);

        // Assert
        assertThat(result).isInstanceOf(Valid.class);
    }
}

