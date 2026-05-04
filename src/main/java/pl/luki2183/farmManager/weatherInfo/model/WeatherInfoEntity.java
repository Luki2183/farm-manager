package pl.luki2183.farmManager.weatherInfo.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class WeatherInfoEntity {
    private Integer humidity;
    private Double windSpeed;
}
