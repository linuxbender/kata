package ch.theforce.kata_04.validation;

import java.util.List;

/**
 * Represents a failed validation result containing validation errors.
 * This record is used to indicate that validation of an object of type T
 * has failed and contains a list of {@link ValidationError} objects describing
 * the validation failures.
 *
 * @param <T> the type of the object that failed validation
 * @param errors a list of validation errors that occurred during validation
 */
public record Invalid<T>(List<ValidationError> errors) implements ValidationResult<T> {}
