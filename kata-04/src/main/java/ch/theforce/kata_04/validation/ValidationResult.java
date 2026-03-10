package ch.theforce.kata_04.validation;

import java.util.List;

/**
 * Sealed interface representing the result of a validation operation.
 * This interface defines a contract for validation results that can be either
 * successful (Valid) or failed (Invalid). It uses Java's sealed class mechanism
 * to restrict implementations to Valid and Invalid records.
 *
 * @param <T> the type of the data being validated, used by Valid to wrap validated objects
 */
@SuppressWarnings("unused")
public sealed interface ValidationResult<T> permits Valid, Invalid {

    /**
     * Creates a successful validation result with the validated data.
     *
     * @param <T> the type of the validated data
     * @param data the validated object to include in the result
     * @return a Valid result containing the provided data
     */
    static <T> ValidationResult<T> ok(T data) {
        return new Valid<>(data);
    }

    /**
     * Creates a failed validation result with a single error.
     *
     * @param <T> the type of the object that failed validation
     * @param field the name of the field that failed validation
     * @param msg a descriptive message explaining the validation failure
     * @return an Invalid result containing the error details
     */
    static <T> ValidationResult<T> error(String field, String msg) {
        return new Invalid<>(List.of(new ValidationError(field, msg)));
    }
}




