package ch.theforce.kata_04.service;

import ch.theforce.kata_04.dto.AddressDto;
import ch.theforce.kata_04.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the AddressServiceImpl class.
 * This test class verifies that the AddressServiceImpl correctly handles
 * validation results and logs appropriate messages for valid and invalid addresses.
 */
@DisplayName("AddressServiceImpl Unit Tests")
@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock
    private ValidationEngine validationEngineMock;

    @InjectMocks
    private AddressServiceImpl addressService;

    private AddressDto addressDto;

    /**
     * Setup method to initialize the service and DTOs before each test.
     */
    @BeforeEach
    void setUp() {
        addressDto = new AddressDto();
        addressDto.setFristName("John");
        addressDto.setLastName("Doe");
        addressDto.setStreet("123 Main St");
        addressDto.setPostalCode("12345");
        addressDto.setCity("Springfield");
    }

    /**
     * Test: ValidationEngine returns a Valid result.
     * Expected: Service logs the valid data and completes successfully.
     */
    @Test
    @DisplayName("Should handle valid address and log data")
    void testSaveAddressWithValidResult() {
        // Arrange
        ValidationResult<AddressDto> validResult = new Valid<>(addressDto);
        when(validationEngineMock.runParallel(addressDto))
                .thenReturn(validResult);

        // Act
        addressService.saveAddress(addressDto);

        // Assert
        verify(validationEngineMock).runParallel(addressDto);
        verify(validationEngineMock, times(1)).runParallel(any());
    }

    /**
     * Test: ValidationEngine returns an Invalid result with single error.
     * Expected: Service logs the validation errors.
     */
    @Test
    @DisplayName("Should handle invalid address with single error")
    void testSaveAddressWithSingleValidationError() {
        // Arrange
        List<ValidationError> errors = List.of(
                new ValidationError("fristName", "First name must not be null")
        );
        ValidationResult<AddressDto> invalidResult = new Invalid<>(errors);
        when(validationEngineMock.runParallel(addressDto))
                .thenReturn(invalidResult);

        // Act
        addressService.saveAddress(addressDto);

        // Assert
        verify(validationEngineMock).runParallel(addressDto);
    }

    /**
     * Test: ValidationEngine returns an Invalid result with multiple errors.
     * Expected: Service logs all validation errors.
     */
    @Test
    @DisplayName("Should handle invalid address with multiple errors")
    void testSaveAddressWithMultipleValidationErrors() {
        // Arrange
        List<ValidationError> errors = List.of(
                new ValidationError("fristName", "First name must not be null"),
                new ValidationError("lastName", "Last name must not be null")
        );
        ValidationResult<AddressDto> invalidResult = new Invalid<>(errors);
        when(validationEngineMock.runParallel(addressDto))
                .thenReturn(invalidResult);

        // Act
        addressService.saveAddress(addressDto);

        // Assert
        verify(validationEngineMock).runParallel(addressDto);
    }

    /**
     * Test: ValidationEngine is called with the provided AddressDto.
     * Expected: The service passes the DTO to the engine without modification.
     */
    @Test
    @DisplayName("Should pass AddressDto to ValidationEngine")
    void testValidationEngineCalledWithCorrectDto() {
        // Arrange
        when(validationEngineMock.runParallel(addressDto))
                .thenReturn(new Valid<>(addressDto));

        // Act
        addressService.saveAddress(addressDto);

        // Assert
        verify(validationEngineMock).runParallel(eq(addressDto));
    }
}

