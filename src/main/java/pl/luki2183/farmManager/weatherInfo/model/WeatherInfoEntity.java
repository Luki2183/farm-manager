package pl.luki2183.farmManager.weatherInfo.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

/**
 * Embeddable data entity, used to store weather info data.
 * <p>Should be used in pair with {@link pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity FieldInfoEntity}.</p>
 */
@Data
@Embeddable
public class WeatherInfoEntity {
    /**
     * Relative humidity level, expressed as a percentage of range 0-100.
     */
    private Integer humidity;
    /**
     * Wind speed, expressed in kilometers per hour (km/h).
     */
    private Double windSpeed;
}
