package pl.luki2183.farmManager.weatherInfo.dto;

import lombok.Data;

/**
 * Data Transfer Object representing weather information for a field.
 *
 * <p>Used to transfer weather data between application layers,
 * corresponding to {@link pl.luki2183.farmManager.weatherInfo.model.WeatherInfoEntity WeatherInfoEntity}.</p>
 */
@Data
public class WeatherInfoDto {
    /**
     * Relative humidity level, expressed as a percentage of range 0-100.
     */
    private Integer humidity;
    /**
     * Wind speed, expressed in kilometers per hour (km/h).
     */
    private Double windSpeed;
}
