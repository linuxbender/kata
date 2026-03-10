package ch.theforce.kata_04.rule.address;

import ch.theforce.kata_04.dto.AddressDto;
import ch.theforce.kata_04.validation.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Validation rule that ensures both address first name and last name are not null.
 * This rule validates that both first name and last name in an AddressDto are present and not null.
 * If either field is null, a separate validation error is returned for that field.
 */
@Component
public class NameNotNull implements ValidationRule<AddressDto> {

    /**
     * Validates that both first name and last name in the address are not null.
     *
     * @param target the AddressDto to validate
     * @return a Valid result if both first name and last name are not null,
     * otherwise an Invalid result with error messages for each null field
     */
    @Override
    public ValidationResult<AddressDto> validate(AddressDto target) {
        List<ValidationError> errors = new ArrayList<>();

        if (Objects.isNull(target.getFirstName())) {
            errors.add(new ValidationError("firstName", "First name must not be null"));
        }
        if (Objects.isNull(target.getLastName())) {
            errors.add(new ValidationError("lastName", "Last name must not be null"));
        }

        return errors.isEmpty()
                ? new Valid<>(target)
                : new Invalid<>(errors);
    }
}
