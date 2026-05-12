package pl.luki2183.farmManager.exception.model;

/**
 * Abstract base exception for resource-not-found scenarios.
 *
 * <p>Subclasses pass the name of the missing resource type to the constructor,
 * which produces a consistent message of the form:
 * {@code "Resource <name> not found"}.</p>
 *
 * <p>Handled globally by
 * {@link pl.luki2183.farmManager.exception.controller.GlobalExceptionHandler GlobalExceptionHandler}
 * and mapped to {@code 404 Not Found}.</p>
 */
public abstract class NotFoundException extends RuntimeException {
    /**
     * Constructs a {@code NotFoundException} for the named resource type.
     *
     * @param message the name of the resource that was not found
     *                (e.g., {@code "FieldEntity"}, {@code "Settings"})
     */
    public NotFoundException(String message) {
        super("Resource " + message + " not found");
    }
}
