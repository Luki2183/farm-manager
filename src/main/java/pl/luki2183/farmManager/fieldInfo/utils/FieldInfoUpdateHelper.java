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

/**
 * Helper component that applies a {@link FieldInfoUpdateDto} onto an existing
 * {@link FieldInfoEntity}, and provides a separate method for updating
 * weather data only.
 *
 * <p>Extracted from {@link pl.luki2183.farmManager.fieldInfo.service.FieldInfoPutService FieldInfoPutService} to keep update logic
 * reusable and independently testable. Only non-null, non-blank values
 * from the DTO are applied; all other fields are left unchanged.</p>
 */
@Slf4j
@Component
@AllArgsConstructor
public class FieldInfoUpdateHelper {

    private final DateFormat dateFormat;

    /**
     * Applies non-null, non-blank fields from {@code dto} onto {@code entity}.
     *
     * @param entity the existing {@link FieldInfoEntity} to modify
     * @param dto    the {@link FieldInfoUpdateDto} containing the candidate updates
     * @return the modified {@link FieldInfoEntity} (same instance, not a copy)
     */
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

    /**
     * Replaces the weather info on {@code existing} with the provided
     * {@link WeatherInfoEntity}, but only if both humidity and wind speed are non-null.
     *
     * @param existing the {@link FieldInfoEntity} whose weather data should be updated
     * @param entity   the new {@link WeatherInfoEntity} to apply
     * @return the modified {@link FieldInfoEntity} (same instance, not a copy)
     */
    public FieldInfoEntity updateWeatherInfo(FieldInfoEntity existing, WeatherInfoEntity entity) {
        log.debug("Entering updateWeatherInfo with field info entity: {}, and weather info entity: {}", existing, entity);
        if (entity.getHumidity() != null && entity.getWindSpeed() != null) {
            existing.setWeatherInfo(entity);
        }
        log.debug("Updated FieldInfoEntity result: {}", existing);
        return existing;
    }
}
