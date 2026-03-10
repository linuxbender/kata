package ch.theforce.kata_04.validation;

/**
 * Represents a single validation error that occurred during validation.
 * This record encapsulates information about a validation failure including
 * the field that failed validation and a descriptive error message.
 *
 * @param field the name of the field that failed validation
 * @param message a descriptive message explaining the validation failure
 */
public record ValidationError(String field, String message) {}
