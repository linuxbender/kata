package ch.kata.kata_02.controller.v1;

import ch.kata.kata_02.dto.v1.CustomerDto;
import ch.kata.kata_02.service.v1.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller class to handle customer-related HTTP requests.
 * Provides endpoints for retrieving, updating, and deleting customers.
 */
@Slf4j
@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * @return ResponseEntity with a list of CustomerDto objects if customers exist, or a 404 Not Found if no customers exist.
     */
    @GetMapping
    ResponseEntity<List<CustomerDto>> getAll() {
        Optional<List<CustomerDto>> optionalCustomers = customerService.getAllCustomers();
        return optionalCustomers.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a customer by ID.
     *
     * @param id the ID of the customer to retrieve.
     * @return a ResponseEntity containing the CustomerDto object if found, or a 404 Not Found if not found.
     */
    @GetMapping("/{id}")
    ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long id) {
        Optional<CustomerDto> optionalCustomer = customerService.getCustomerById(id);
        return optionalCustomer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Updates an existing customer.
     *
     * @param id          the ID of the customer to update.
     * @param customerDto the CustomerDto object containing updated customer information.
     * @return a ResponseEntity containing the updated CustomerDto object, or a 404 Not Found if the customer does not exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
        Optional<CustomerDto> optionalCustomerDto = customerService.getCustomerById(id);

        if (optionalCustomerDto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CustomerDto customer = optionalCustomerDto.get();
        customer.setName(customerDto.getName());
        CustomerDto updatedCustomerDto = customerService.updateCustomer(customer);
        return ResponseEntity.ok(updatedCustomerDto);
    }

    /**
     * Deletes a customer by ID.
     *
     * @param id the ID of the customer to delete.
     * @return a ResponseEntity indicating the result of the deletion operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerDto> deleteCustomer(@PathVariable Long id) {
        Optional<CustomerDto> optionalCustomerDto = customerService.getCustomerById(id);

        if (optionalCustomerDto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CustomerDto customer = optionalCustomerDto.get();
        customerService.deleteCustomer(customer);
        return ResponseEntity.noContent().build();
    }
}
