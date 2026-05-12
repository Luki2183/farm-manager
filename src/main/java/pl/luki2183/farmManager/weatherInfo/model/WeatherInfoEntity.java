package pl.luki2183.farmManager.weatherInfo.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;

/**
 * Embeddable data entity, used to store weather info data.
 * <p>Should be used in pair with {@link FieldInfoEntity FieldInfoEntity}.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
