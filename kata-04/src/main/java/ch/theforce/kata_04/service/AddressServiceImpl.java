package ch.theforce.kata_04.service;

import ch.theforce.kata_04.dto.AddressDto;
import ch.theforce.kata_04.validation.*;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the AddressService interface.
 * This service handles the validation and processing of address data.
 * It uses the ValidationEngine to run all validation rules in parallel and logs
 * the results based on whether the validation passed or failed.
 */
@Service
@AllArgsConstructor
@Log4j2
public class AddressServiceImpl implements AddressService {

    private final ValidationEngine engine;

    /**
     * Saves an address by validating it and logging the result.
     * The validation is performed using the ValidationEngine which executes
     * all applicable validation rules in parallel. If validation passes, the
     * data is logged. If validation fails, all errors are logged.
     *
     * @param addressDto the address data transfer object to be validated and saved
     */
    @Override
    public void saveAddress(AddressDto addressDto) {

        ValidationResult<AddressDto> baseCheck = engine.runParallel(addressDto);

        switch (baseCheck) {
            case Valid(var data) -> log.info(data.toString());
            case Invalid(List<ValidationError> errors) -> log.info(errors.toString());
        }
    }
}


