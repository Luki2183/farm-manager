package pl.luki2183.farmManager.weatherInfo.mapper;

import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.weatherInfo.dto.WeatherDto;
import pl.luki2183.farmManager.weatherInfo.model.WeatherInfoEntity;

@Component
public class WeatherInfoMapper {
    public WeatherInfoEntity fromDto(WeatherDto dto) {
        WeatherInfoEntity weatherInfo = new WeatherInfoEntity();
        weatherInfo.setHumidity(dto.getRelativeHumidity());
        weatherInfo.setWindSpeed(dto.getWind().getSpeed().getValue());
        return weatherInfo;
    }
}
