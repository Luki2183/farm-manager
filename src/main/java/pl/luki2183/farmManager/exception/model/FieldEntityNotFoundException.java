package pl.luki2183.farmManager.exception.model;

/**
 * Thrown when a {@link pl.luki2183.farmManager.fields.model.FieldEntity FieldEntity}
 * cannot be found by its identifier.
 *
 * <p>Produces the message: {@code "Resource FieldEntity not found"}.</p>
 */
public class FieldEntityNotFoundException extends NotFoundException {
    /** Constructs a {@code FieldEntityNotFoundException} with a fixed resource name. */
    public FieldEntityNotFoundException() {
        super("FieldEntity");
    }
}
