package pl.luki2183.farmManager.exception.model;

/**
 * Thrown when the singleton
 * {@link pl.luki2183.farmManager.settings.model.SettingsEntity SettingsEntity}
 * record cannot be found in the database.
 *
 * <p>Produces the message: {@code "Resource Settings not found"}.</p>
 */
public class SettingsEntityNotFoundException extends NotFoundException {
    /** Constructs a {@code SettingsEntityNotFoundException} with a fixed resource name. */
    public SettingsEntityNotFoundException() {
        super("Settings");
    }
}
