package ch.theforce.kata_04.validation;

/**
 * Interface for defining validation rules that can be applied to objects.
 * Implementations of this interface define specific business rules for
 * validating objects of type T. Each rule returns a {@link ValidationResult}
 * indicating whether the validation passed or failed.
 *
 * @param <T> the type of object being validated
 */
public interface ValidationRule<T> {

    /**
     * Validates the given target object against this rule.
     *
     * @param target the object to validate
     * @return a {@link ValidationResult} containing either the valid object
     *         or a list of validation errors if validation failed
     */
    ValidationResult<T> validate(T target);
}