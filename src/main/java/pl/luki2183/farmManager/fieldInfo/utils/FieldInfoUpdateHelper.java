package pl.luki2183.farmManager.fieldInfo.utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoUpdateDto;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.model.Grain;
import pl.luki2183.farmManager.utils.DateFormat;
import pl.luki2183.farmManager.weatherInfo.model.WeatherInfoEntity;

import java.time.LocalDate;

@Slf4j
@Component
@AllArgsConstructor
public class FieldInfoUpdateHelper {

    private final DateFormat dateFormat;

    public FieldInfoEntity update(FieldInfoEntity entity, FieldInfoUpdateDto dto) {
        log.debug("Entering update with entity: {}, and dto: {}", entity, dto);
        if (dto.getSurfaceArea() != null && !dto.getSurfaceArea().isNaN()) entity.setSurfaceArea(dto.getSurfaceArea());
        if (dto.getGrainType() != null && !dto.getGrainType().isBlank()) entity.setGrainType(Grain.valueOf(dto.getGrainType()));
        if (dto.getPlantDate() != null && !dto.getPlantDate().isBlank()) entity.setPlantDate(LocalDate.parse(dto.getPlantDate(), dateFormat.getDateFormat()));
        if (dto.getExpectedHarvestDate() != null && !dto.getExpectedHarvestDate().isBlank()) entity.setExpectedHarvestDate(LocalDate.parse(dto.getExpectedHarvestDate(), dateFormat.getDateFormat()));
        if (dto.getFieldName() != null && !dto.getFieldName().isBlank()) entity.setFieldName(dto.getFieldName());
        log.debug("Updated FieldInfoEntity result: {}", entity);
        return entity;
    }

    public FieldInfoEntity updateWeatherInfo(FieldInfoEntity existing, WeatherInfoEntity entity) {
        log.debug("Entering updateWeatherInfo with field info entity: {}, and weather info entity: {}", existing, entity);
        if (entity.getHumidity() != null && entity.getWindSpeed() != null) {
            existing.setWeatherInfo(entity);
        }
        log.debug("Updated FieldInfoEntity result: {}", existing);
        return existing;
    }
}
