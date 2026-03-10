package ch.theforce.kata_04.controller;

import ch.theforce.kata_04.dto.AddressDto;
import ch.theforce.kata_04.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for the AddressController class.
 * This test class verifies that the controller correctly handles HTTP requests
 * for address operations and delegates to the service layer.
 */
@DisplayName("AddressController Unit Tests")
@ExtendWith(MockitoExtension.class)
class AddressControllerTest {

    @Mock
    private AddressService addressServiceMock;

    @InjectMocks
    private AddressController addressController;

    private AddressDto addressDto;

    /**
     * Setup method to initialize the controller and DTOs before each test.
     */
    @BeforeEach
    void setUp() {
        addressDto = new AddressDto();
        addressDto.setFirstName("John");
        addressDto.setLastName("Doe");
        addressDto.setStreet("123 Main St");
        addressDto.setPostalCode("12345");
        addressDto.setCity("Springfield");
    }

    /**
     * Test: POST /address endpoint is called with valid AddressDto.
     * Expected: Service is called and OK status is returned.
     */
    @Test
    @DisplayName("Should save address and return OK status for POST /address")
    void testPostAddressCallsServiceAndReturnsOkStatus() {
        // Act
        ResponseEntity<String> response = addressController.postAddress(addressDto);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Address saved");
        verify(addressServiceMock).saveAddress(addressDto);
    }

    /**
     * Test: POST /address endpoint delegates to service layer.
     * Expected: Service receives the exact AddressDto passed to controller.
     */
    @Test
    @DisplayName("Should pass AddressDto to service layer")
    void testPostAddressPassesDtoToService() {
        // Act
        addressController.postAddress(addressDto);

        // Assert
        verify(addressServiceMock).saveAddress(addressDto);
    }
}

