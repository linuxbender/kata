package ch.theforce.kata_04.controller;

import ch.theforce.kata_04.dto.AddressDto;
import ch.theforce.kata_04.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * REST controller for managing address operations.
 * This controller provides HTTP endpoints for saving address data.
 * All address operations are delegated to the AddressService layer for processing and validation.
 */
@Controller
@AllArgsConstructor
public class AddressController {

    private final AddressService addressService;

    /**
     * Saves an address.
     * This endpoint accepts address data in the request body, delegates it to the service layer
     * for validation and processing, and returns a confirmation response.
     *
     * @param addressDto the address data transfer object containing address details
     * @return a ResponseEntity with HTTP status OK and success message
     */
    @PostMapping("/address")
    public ResponseEntity<String> postAddress(@RequestBody AddressDto addressDto) {
        addressService.saveAddress(addressDto);
        return ResponseEntity.ok("Address saved");
    }
}
