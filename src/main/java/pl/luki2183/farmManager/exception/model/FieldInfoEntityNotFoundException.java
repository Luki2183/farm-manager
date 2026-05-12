package pl.luki2183.farmManager.exception.model;

/**
 * Thrown when a {@link pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity FieldInfoEntity}
 * cannot be found by its identifier.
 *
 * <p>Produces the message: {@code "Resource FieldInfo not found"}.</p>
 */
public class FieldInfoEntityNotFoundException extends NotFoundException {
    /** Constructs a {@code FieldInfoEntityNotFoundException} with a fixed resource name. */
    public FieldInfoEntityNotFoundException() {
        super("FieldInfo");
    }
}
