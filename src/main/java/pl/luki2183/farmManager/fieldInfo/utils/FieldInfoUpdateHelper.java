package pl.luki2183.farmManager.fieldInfo.utils;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoUpdateDto;
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
    public FieldInfoEntity update(FieldInfoEntity entity, FieldInfoUpdateDto dto) {
        entity.setSurfaceArea(dto.getSurfaceArea());
        entity.setGrainType(Grain.valueOf(dto.getGrainType()));
        entity.setPlantDate(LocalDate.parse(dto.getPlantDate(), dateFormat.getDateFormat()));
        entity.setExpectedHarvestDate(LocalDate.parse(dto.getExpectedHarvestDate(), dateFormat.getDateFormat()));
        entity.setFieldColor(Color.decode(dto.getFieldColor()));
        return entity;
    }
}
