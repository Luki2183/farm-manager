package pl.luki2183.farmManager.weatherInfo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.weatherInfo.model.WeatherInfoEntity;
import pl.luki2183.farmManager.fields.model.PointEntity;

@Service
@AllArgsConstructor
public class WeatherGetService {

    public WeatherInfoEntity getWeatherInfo(PointEntity point) {
//        todo getWeather by lat and lng
        WeatherInfoEntity weatherInfoEntity = new WeatherInfoEntity();
        weatherInfoEntity.setHumidity(0.0);
        weatherInfoEntity.setWindSpeed(0.0);
        return weatherInfoEntity;
    }
}
