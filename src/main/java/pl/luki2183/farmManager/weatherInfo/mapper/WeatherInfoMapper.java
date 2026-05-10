package pl.luki2183.farmManager.weatherInfo.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.weatherInfo.dto.WeatherDto;
import pl.luki2183.farmManager.weatherInfo.model.WeatherInfoEntity;

/**
 * Mapping class for WeatherInfo.
 */
@Slf4j
@Component
public class WeatherInfoMapper {
    /**
     * Creates a {@code WeatherInfoEntity} object from provided data transfer object.
     * @param dto object of {@link WeatherDto} class
     * @return {@link pl.luki2183.farmManager.weatherInfo.model.WeatherInfoEntity}
     */
    public WeatherInfoEntity fromDto(WeatherDto dto) {
        log.debug("Entering fromDto with params: {}", dto);
        WeatherInfoEntity weatherInfo = new WeatherInfoEntity();
        weatherInfo.setHumidity(dto.getRelativeHumidity());
        weatherInfo.setWindSpeed(dto.getWind().getSpeed().getValue());
        log.debug("Mapped WeatherDto to WeatherInfoEntity: {}", weatherInfo);
        return weatherInfo;
    }
}
