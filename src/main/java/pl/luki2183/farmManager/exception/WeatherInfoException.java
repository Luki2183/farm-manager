package pl.luki2183.farmManager.exception;

public class WeatherInfoException extends RuntimeException {
    public WeatherInfoException(String message) {
        super(message);
    }

    public WeatherInfoException(String message, Throwable cause) {
        super(message, cause);
    }
}
