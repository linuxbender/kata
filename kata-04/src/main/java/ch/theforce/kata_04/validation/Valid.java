package ch.theforce.kata_04.validation;

/**
 * Represents a successful validation result containing the validated data.
 * This record is used to indicate that validation of an object of type T
 * has succeeded and contains the validated object itself.
 *
 * @param <T> the type of the validated object
 * @param data the object that passed validation
 */
public record Valid<T>(T data) implements ValidationResult<T> {}
