package ch.theforce.kata_04.service;

import ch.theforce.kata_04.dto.AddressDto;

/**
 * Service interface for managing address operations.
 * This service provides business logic for handling address data,
 * including validation and persistence of address information.
 */
public interface AddressService {

    /**
     * Saves an address.
     * This method persists the provided address data to the database.
     * The address data is validated before being saved.
     *
     * @param addressDto the address data transfer object containing
     *                   the address details to be saved
     */
    void saveAddress(AddressDto addressDto);
}


