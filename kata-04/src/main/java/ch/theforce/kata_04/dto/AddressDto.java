package ch.theforce.kata_04.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for address information.
 * This DTO encapsulates address details including first name, last name,
 * street address, postal code, and city.
 */
@Getter
@Setter
public class AddressDto {
    private String fristName;
    private String lastName;
    private String street;
    private String postalCode;
    private String city;
}
