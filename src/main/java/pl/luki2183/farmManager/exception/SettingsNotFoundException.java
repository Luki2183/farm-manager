package pl.luki2183.farmManager.exception;

public class SettingsNotFoundException extends NotFoundException {
    public SettingsNotFoundException() {
        super("Settings");
    }
}
