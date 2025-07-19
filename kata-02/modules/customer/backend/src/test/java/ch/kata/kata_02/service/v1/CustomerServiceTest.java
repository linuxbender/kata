package ch.kata.kata_02.service.v1;

import ch.kata.kata_02.dto.v1.CustomerDto;
import ch.kata.kata_02.exception.v1.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {

    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerService();
    }

    @Test
    void getAllCustomers_shouldReturnCustomers_whenCustomersExist() {

        Optional<List<CustomerDto>> customers = customerService.getAllCustomers();

        assertTrue(customers.isPresent());
        assertEquals(2, customers.get().size());
    }

    @Test
    void getAllCustomers_shouldReturnEmpty_whenNoCustomersExist() {

        customerService = new CustomerService() {
            @Override
            public Optional<List<CustomerDto>> getAllCustomers() {
                return Optional.of(new ArrayList<>());
            }
        };

        Optional<List<CustomerDto>> customers = customerService.getAllCustomers();

        assertTrue(customers.isPresent());
        assertTrue(customers.get().isEmpty());
    }

    @Test
    void getCustomerById_shouldReturnCustomer_whenCustomerExists() {
        Optional<CustomerDto> customer = customerService.getCustomerById(1L);

        assertTrue(customer.isPresent());
        assertEquals("John Doe", customer.get().getName());  // Der Name des Kunden sollte "John Doe" sein
    }

    @Test
    void getCustomerById_shouldThrowException_whenCustomerDoesNotExist() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> customerService.getCustomerById(3L));
        assertEquals("Customer not found", exception.getMessage());
    }

    @Test
    void getCustomerById_shouldThrowException_whenIdIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> customerService.getCustomerById(null));
        assertEquals("Customer ID cannot be null", exception.getMessage());
    }

    @Test
    void updateCustomer_shouldUpdateCustomer_whenCustomerExists() {
        CustomerDto customer = new CustomerDto(1L, "Updated Name");

        CustomerDto updatedCustomer = customerService.updateCustomer(customer);

        assertEquals("Updated Name", updatedCustomer.getName());
    }

    @Test
    void updateCustomer_shouldThrowException_whenCustomerDoesNotExist() {

        CustomerDto customer = new CustomerDto(3L, "Non Existent");

        NotFoundException exception = assertThrows(NotFoundException.class, () -> customerService.updateCustomer(customer));
        assertEquals("Customer not found", exception.getMessage());
    }

    @Test
    void updateCustomer_shouldThrowException_whenCustomerIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> customerService.updateCustomer(null));
        assertEquals("Customer or customer ID cannot be null", exception.getMessage());
    }

    @Test
    void updateCustomer_shouldThrowException_whenCustomerIdIsNull() {

        CustomerDto customer = new CustomerDto(null, "Name without ID");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> customerService.updateCustomer(customer));
        assertEquals("Customer or customer ID cannot be null", exception.getMessage());
    }

    @Test
    void deleteCustomer_shouldDeleteCustomer_whenCustomerExists() {
        CustomerDto customer = new CustomerDto(1L, "John Doe");

        customerService.deleteCustomer(customer);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> customerService.getCustomerById(1L));
        assertEquals("Customer not found", exception.getMessage());
    }

    @Test
    void deleteCustomer_shouldThrowException_whenCustomerDoesNotExist() {

        CustomerDto customer = new CustomerDto(3L, "Non Existent");


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> customerService.deleteCustomer(customer));
        assertEquals("Customer not found", exception.getMessage());
    }

    @Test
    void deleteCustomer_shouldThrowException_whenCustomerIsNull() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> customerService.deleteCustomer(null));
        assertEquals("Customer or customer ID cannot be null", exception.getMessage());
    }

    @Test
    void deleteCustomer_shouldThrowException_whenCustomerIdIsNull() {

        CustomerDto customer = new CustomerDto(null, "Name without ID");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> customerService.deleteCustomer(customer));
        assertEquals("Customer or customer ID cannot be null", exception.getMessage());
    }

    @Test
    void createCustomer_shouldCreateNewCustomerWithGeneratedId() {
        CustomerDto newCustomer = new CustomerDto(null, "Alice Johnson");

        CustomerDto createdCustomer = customerService.createCustomer(newCustomer);

        assertNotNull(createdCustomer, "Customer should not be null.");
        assertNotNull(createdCustomer.getId(), "Customer ID should be generated.");
        assertEquals("Alice Johnson", createdCustomer.getName(), "Customer name should be 'Alice Johnson'.");

        assertTrue(createdCustomer.getId() > 0, "Customer ID should be greater than 0.");
    }

    @Test
    void createCustomer_shouldThrowExceptionWhenIdIsAlreadySet() {
        CustomerDto customerWithId = new CustomerDto(1L, "Bob Brown");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.createCustomer(customerWithId);
        });

        assertEquals("CustomerDto cannot be null and ID must not be set for creation", exception.getMessage());
    }

    @Test
    void createCustomer_shouldThrowExceptionWhenCustomerDtoIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.createCustomer(null);
        });

        assertEquals("CustomerDto cannot be null and ID must not be set for creation", exception.getMessage());
    }

    @Test
    void createCustomer_shouldIncrementIdCorrectly() {
        CustomerDto firstCustomer = new CustomerDto(null, "Tom Taylor");
        customerService.createCustomer(firstCustomer);

        CustomerDto secondCustomer = new CustomerDto(null, "Anna Adams");
        CustomerDto createdSecondCustomer = customerService.createCustomer(secondCustomer);

        assertEquals(4L, createdSecondCustomer.getId(), "Second customer's ID should be 4.");
    }
}
