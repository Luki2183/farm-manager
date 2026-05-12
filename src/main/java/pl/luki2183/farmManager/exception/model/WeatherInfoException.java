package pl.luki2183.farmManager.exception.model;

/**
 * Thrown when the external weather API is unreachable, returns an empty response,
 * or otherwise fails to provide usable weather data.
 *
 * <p>Mapped to {@code 502 Bad Gateway} by
 * {@link pl.luki2183.farmManager.exception.controller.GlobalExceptionHandler GlobalExceptionHandler}.</p>
 */
public class WeatherInfoException extends RuntimeException {
    /**
     * Constructs a {@code WeatherInfoException} with the given detail message.
     *
     * @param message description of the failure
     */
    public WeatherInfoException(String message) {
        super(message);
    }

    /**
     * Constructs a {@code WeatherInfoException} with a detail message and a root cause.
     *
     * @param message description of the failure
     * @param cause   the underlying exception that caused this failure
     */
    public WeatherInfoException(String message, Throwable cause) {
        super(message, cause);
    }
}
