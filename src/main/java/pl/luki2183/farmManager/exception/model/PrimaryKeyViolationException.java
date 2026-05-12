package pl.luki2183.farmManager.exception.model;

/**
 * Thrown when a POST request attempts to create a resource that already exists,
 * resulting in a primary key conflict.
 *
 * <p>Mapped to {@code 409 Conflict} by
 * {@link pl.luki2183.farmManager.exception.controller.GlobalExceptionHandler GlobalExceptionHandler}.</p>
 */
public class PrimaryKeyViolationException extends RuntimeException {
    /** Constructs a {@code PrimaryKeyViolationException} with a fixed conflict message. */
    public PrimaryKeyViolationException() {
        super("Cannot process POST request. Resource with the same data already exists.");
    }
}
