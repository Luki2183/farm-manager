package pl.luki2183.farmManager.fieldInfo.mapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoDto;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.model.Grain;
import pl.luki2183.farmManager.fieldInfo.utils.ColorParser;
import pl.luki2183.farmManager.utils.DateFormat;
import pl.luki2183.farmManager.weatherInfo.model.WeatherInfoEntity;
import pl.luki2183.farmManager.fields.model.FieldEntity;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class FieldInfoMapper {

    private final DateFormat dateFormat;
    private final ColorParser colorParser;

    public List<FieldInfoDto> infoToDtoList(List<FieldInfoEntity> all) {
        return all.stream()
                .map(this::infoToDto)
                .toList();
    }

    public FieldInfoDto infoToDto(FieldInfoEntity entity) {
        FieldInfoDto dto = new FieldInfoDto();
        dto.setFieldId(entity.getFieldId());
        dto.setSurfaceArea(entity.getSurfaceArea());
        dto.setGrainType(entity.getGrainType().toString());
        dto.setPlantDate(entity.getPlantDate().format(dateFormat.getDateFormat()));
        dto.setExpectedHarvestDate(entity.getExpectedHarvestDate().format(dateFormat.getDateFormat()));
        dto.setHumidity(entity.getWeatherInfo().getHumidity());
        dto.setWindSpeed(entity.getWeatherInfo().getWindSpeed());
        dto.setFieldColor(entity.getFieldColor().toString());

        return dto;
    }

    public FieldInfoEntity dtoToInfo(FieldInfoDto dto, FieldEntity entityToBind, WeatherInfoEntity weatherInfoToBind) {
//        todo expectedHarvestDate = automatic update with internal logic
        return FieldInfoEntity.builder()
                .fieldId(entityToBind.getId())
                .field(entityToBind)
                .surfaceArea(dto.getSurfaceArea())
                .grainType(Grain.valueOf(dto.getGrainType()))
                .plantDate(LocalDate.parse(dto.getPlantDate(), dateFormat.getDateFormat()))
                .expectedHarvestDate(LocalDate.parse(dto.getExpectedHarvestDate(), dateFormat.getDateFormat()))
                .weatherInfo(weatherInfoToBind)
                .fieldColor(colorParser.parse(dto.getFieldColor()))
                .build();
    }
}
