package ch.kata.kata_02.exception.v1;

/**
 * Custom exception class to handle cases where a requested resource is not found.
 * This exception extends RuntimeException and can be thrown when a resource is not found in the system.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}