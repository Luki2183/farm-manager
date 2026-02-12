package pl.luki2183.farmManager.fieldInfo.mapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoDto;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoUpdateDto;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.model.Grain;
import pl.luki2183.farmManager.fieldInfo.utils.ColorConverter;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.utils.DateFormat;
import pl.luki2183.farmManager.weatherInfo.model.WeatherInfoEntity;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class FieldInfoMapper {

    private final DateFormat dateFormat;
    private final ColorConverter colorConverter;

    public List<FieldInfoDto> infoToDtoList(List<FieldInfoEntity> all) {
        return all.stream()
                .map(this::infoToDto)
                .toList();
    }

    public FieldInfoDto infoToDto(FieldInfoEntity entity) {
        FieldInfoDto dto = new FieldInfoDto();
        dto.setFieldId(entity.getFieldId());
        dto.setSurfaceArea(Math.round(entity.getSurfaceArea()*100.)/100.);
        dto.setGrainType(entity.getGrainType().toString());
        dto.setPlantDate(entity.getPlantDate().format(dateFormat.getDateFormat()));
        dto.setExpectedHarvestDate(entity.getExpectedHarvestDate().format(dateFormat.getDateFormat()));
        dto.setHumidity(entity.getWeatherInfo().getHumidity());
        dto.setWindSpeed(entity.getWeatherInfo().getWindSpeed());
        dto.setFieldColor(colorConverter.toHexString(entity.getFieldColor()));
        return dto;
    }

    public FieldInfoEntity dtoToInfo(FieldInfoUpdateDto dto, FieldEntity entityToBind, WeatherInfoEntity weatherInfoToBind) {
//        todo expectedHarvestDate = automatic update with internal logic
        return FieldInfoEntity.builder()
                .fieldId(entityToBind.getFieldId())
                .field(entityToBind)
                .surfaceArea(dto.getSurfaceArea())
                .grainType(Grain.valueOf(dto.getGrainType()))
                .plantDate(LocalDate.parse(dto.getPlantDate(), dateFormat.getDateFormat()))
                .expectedHarvestDate(LocalDate.parse(dto.getExpectedHarvestDate(), dateFormat.getDateFormat()))
                .weatherInfo(weatherInfoToBind)
                .fieldColor(Color.decode(dto.getFieldColor()))
                .build();
    }
}
