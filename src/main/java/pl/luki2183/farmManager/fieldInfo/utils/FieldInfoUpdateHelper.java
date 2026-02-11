package pl.luki2183.farmManager.fieldInfo.utils;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoDto;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.model.Grain;
import pl.luki2183.farmManager.utils.DateFormat;
import pl.luki2183.farmManager.weatherInfo.model.WeatherInfoEntity;

import java.awt.*;
import java.time.LocalDate;

@Component
@AllArgsConstructor
public class FieldInfoUpdateHelper {

    private final DateFormat dateFormat;

    @Transactional
    public FieldInfoEntity update(FieldInfoEntity entity, FieldInfoDto dto) {
        entity.setSurfaceArea(dto.getSurfaceArea());
        entity.setGrainType(Grain.valueOf(dto.getGrainType()));
        entity.setPlantDate(LocalDate.parse(dto.getPlantDate(), dateFormat.getDateFormat()));
        entity.setExpectedHarvestDate(LocalDate.parse(dto.getExpectedHarvestDate(), dateFormat.getDateFormat()));
        WeatherInfoEntity weatherInfo = new WeatherInfoEntity();
        weatherInfo.setHumidity(dto.getHumidity());
        weatherInfo.setWindSpeed(dto.getWindSpeed());
        entity.setWeatherInfo(weatherInfo);
        entity.setFieldColor(Color.decode(dto.getFieldColor()));
        return entity;
    }
}
