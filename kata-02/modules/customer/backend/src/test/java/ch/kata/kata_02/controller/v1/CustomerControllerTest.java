package ch.kata.kata_02.controller.v1;

import ch.kata.kata_02.dto.v1.CustomerDto;
import ch.kata.kata_02.service.v1.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;
    private CustomerController customerController;
    private CustomerDto validCustomer;
    private CustomerDto customerWithId;

    @BeforeEach
    void setUp() {
        customerController = new CustomerController(customerService);
        validCustomer = new CustomerDto(null, "Alice Johnson");
        customerWithId = new CustomerDto(1L, "Bob Brown");
    }

    @Test
    void getAll_shouldReturnListOfCustomers_whenCustomersExist() {
        List<CustomerDto> customers = Arrays.asList(new CustomerDto(1L, "Alice"), new CustomerDto(2L, "Bob"));
        when(customerService.getAllCustomers()).thenReturn(Optional.of(customers));

        ResponseEntity<List<CustomerDto>> response = customerController.getAll();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getAll_shouldReturn404_whenNoCustomersExist() {
        when(customerService.getAllCustomers()).thenReturn(Optional.empty());

        ResponseEntity<List<CustomerDto>> response = customerController.getAll();

        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    @Test
    void getCustomerById_shouldReturnCustomer_whenFound() {
        CustomerDto customer = new CustomerDto(1L, "Alice");
        when(customerService.getCustomerById(1L)).thenReturn(Optional.of(customer));

        ResponseEntity<CustomerDto> response = customerController.getCustomerById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Alice", response.getBody().getName());
    }

    @Test
    void getCustomerById_shouldReturn404_whenNotFound() {
        when(customerService.getCustomerById(1L)).thenReturn(Optional.empty());

        ResponseEntity<CustomerDto> response = customerController.getCustomerById(1L);

        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    @Test
    void updateCustomer_shouldReturnUpdatedCustomer_whenCustomerExists() {
        CustomerDto existing = new CustomerDto(1L, "OldName");
        CustomerDto update = new CustomerDto(1L, "NewName");
        CustomerDto updated = new CustomerDto(1L, "NewName");

        when(customerService.getCustomerById(1L)).thenReturn(Optional.of(existing));
        when(customerService.updateCustomer(any(CustomerDto.class))).thenReturn(updated);

        ResponseEntity<CustomerDto> response = customerController.updateCustomer(1L, update);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("NewName", response.getBody().getName());
    }

    @Test
    void updateCustomer_shouldReturn404_whenCustomerDoesNotExist() {
        CustomerDto update = new CustomerDto(1L, "NewName");
        when(customerService.getCustomerById(1L)).thenReturn(Optional.empty());

        ResponseEntity<CustomerDto> response = customerController.updateCustomer(1L, update);

        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    @Test
    void deleteCustomer_shouldReturn204_whenCustomerExists() {
        CustomerDto existing = new CustomerDto(1L, "ToDelete");
        when(customerService.getCustomerById(1L)).thenReturn(Optional.of(existing));

        ResponseEntity<CustomerDto> response = customerController.deleteCustomer(1L);

        assertEquals(204, response.getStatusCode().value());
        verify(customerService).deleteCustomer(existing);
    }

    @Test
    void deleteCustomer_shouldReturn404_whenCustomerDoesNotExist() {
        when(customerService.getCustomerById(1L)).thenReturn(Optional.empty());

        ResponseEntity<CustomerDto> response = customerController.deleteCustomer(1L);

        assertEquals(404, response.getStatusCode().value());
        verify(customerService, never()).deleteCustomer(any());
    }

    @Test
    void createCustomer_shouldReturnCreatedCustomerWhenValidCustomer() {
        CustomerDto createdCustomer = new CustomerDto(1L, "Alice Johnson");

        when(customerService.createCustomer(validCustomer)).thenReturn(createdCustomer);

        ResponseEntity<CustomerDto> result = customerController.createCustomer(validCustomer);

        assertNotNull(result.getBody());
        assertEquals(1L, result.getBody().getId());
        assertEquals("Alice Johnson", result.getBody().getName());

        verify(customerService, times(1)).createCustomer(validCustomer);
    }

    @Test
    void createCustomer_shouldReturnNullWhenIdIsProvided() {
        ResponseEntity<CustomerDto> result = customerController.createCustomer(customerWithId);

        assertEquals(400, result.getStatusCode().value());

        verify(customerService, never()).createCustomer(customerWithId);
    }

    @Test
    void createCustomer_shouldReturnNullWhenCustomerDtoHasEmptyName() {
        CustomerDto invalidCustomer = new CustomerDto(null, "");

        ResponseEntity<CustomerDto> result = customerController.createCustomer(invalidCustomer);

        assertEquals(400, result.getStatusCode().value());

        verify(customerService, never()).createCustomer(invalidCustomer);
    }

    @Test
    void createCustomer_shouldReturnNullWhenCustomerDtoHasNullName() {
        CustomerDto invalidCustomer = new CustomerDto(null, null);

        ResponseEntity<CustomerDto> result = customerController.createCustomer(invalidCustomer);

        assertEquals(400, result.getStatusCode().value());

        verify(customerService, never()).createCustomer(invalidCustomer);
    }


}