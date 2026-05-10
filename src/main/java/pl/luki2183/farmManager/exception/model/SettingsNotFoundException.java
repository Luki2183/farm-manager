package pl.luki2183.farmManager.exception.model;

public class SettingsNotFoundException extends NotFoundException {
    public SettingsNotFoundException() {
        super("Settings");
    }
}
