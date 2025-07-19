package ch.kata.kata_02.service.v1;

import ch.kata.kata_02.dto.v1.CustomerDto;
import ch.kata.kata_02.exception.v1.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class to manage customer operations.
 */
@Slf4j
@Service
public class CustomerService {

    List<CustomerDto> customers = new ArrayList<>();

    /**
     * Constructor to initialize the service with some sample customers.
     */
    public CustomerService() {
        customers.add(new CustomerDto(1L, "John Doe"));
        customers.add(new CustomerDto(2L, "Jane Smith"));
    }

    /**
     * Retrieves all customers.
     *
     * @return an Optional containing a list of CustomerDto objects, or empty if no customers exist.
     */
    public Optional<List<CustomerDto>> getAllCustomers() {
        return Optional.ofNullable(customers);
    }

    /**
     * Retrieves a customer by their ID.
     *
     * @param id the ID of the customer to retrieve.
     * @return an Optional containing the CustomerDto object if found, or empty if not found.
     */
    public Optional<CustomerDto> getCustomerById(Long id) {

        if (id == null) {
            log.info("Customer ID is null, cannot retrieve customer.");
            throw new IllegalArgumentException("Customer ID cannot be null");
        }

        return Optional.ofNullable(customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Customer not found")));
    }

    /**
     * Updates an existing customer.
     *
     * @param customer the CustomerDto object containing updated customer information.
     * @return the updated CustomerDto object.
     */
    public CustomerDto updateCustomer(CustomerDto customer) {

        if (customer == null || customer.getId() == null) {
            log.info("Customer or customer ID is null, update not possible.");
            throw new IllegalArgumentException("Customer or customer ID cannot be null");
        }

        customers.stream()
                .filter(c -> c.getId().equals(customer.getId()))
                .findFirst()
                .ifPresent(existingCustomer -> existingCustomer.setName(customer.getName()));

        return customers.stream()
                .filter(it -> it.getId().equals(customer.getId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Customer not found"));
    }

    /**
     * Deletes a customer.
     *
     * @param customer the CustomerDto object to be deleted.
     */
    public void deleteCustomer(CustomerDto customer) {

        if (customer == null || customer.getId() == null) {
            log.info("Customer or customer ID is null, cannot delete.");
            throw new IllegalArgumentException("Customer or customer ID cannot be null");
        }

        boolean isDeleted = customers.removeIf(it -> it.getId().equals(customer.getId()));

        if (!isDeleted) {
            log.info("Customer with ID {} not found for deletion.", customer.getId());
            throw new IllegalArgumentException("Customer not found");
        }
    }

    /**
     * Creates a new customer.
     *
     * @param customerDto the CustomerDto object to be created.
     * @return the created CustomerDto object with an assigned ID.
     */
    public CustomerDto createCustomer(CustomerDto customerDto) {
        if (customerDto == null || customerDto.getId() != null) {
            log.warn("CustomerDto is null or ID is already set. Cannot create customer.");
            throw new IllegalArgumentException("CustomerDto cannot be null and ID must not be set for creation");
        }

        long newId = customers.stream().mapToLong(CustomerDto::getId).max().orElse(0) + 1;
        customerDto.setId(newId);
        customers.add(customerDto);
        return customerDto;
    }
}
