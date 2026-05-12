package pl.luki2183.farmManager.fieldInfo.mapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoCreateDto;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoDto;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoListDto;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoUpdateDto;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.model.Grain;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.utils.DateFormat;
import pl.luki2183.farmManager.weatherInfo.model.WeatherInfoEntity;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class FieldInfoMapper {

    private final DateFormat dateFormat;

    public FieldInfoListDto infoToDtoList(List<FieldInfoEntity> all) {
        log.debug("Mapping FieldInfoEntity list to FieldInfoListDto: {}", all);
        List<FieldInfoDto> list = all.stream()
                .map(this::infoToDto)
                .toList();
        FieldInfoListDto result = new FieldInfoListDto();
        result.setDtoList(list);
        result.setCount(list.size());
        log.debug("Mapped FieldInfoListDto result: {}", result);
        return result;
    }

    public FieldInfoDto infoToDto(FieldInfoEntity entity) {
        log.debug("Mapping FieldInfoEntity to FieldInfoDto: {}", entity);
        FieldInfoDto dto = new FieldInfoDto();
        dto.setFieldId(entity.getFieldId());
        dto.setSurfaceArea(Math.round(entity.getSurfaceArea()*100.)/100.);
        dto.setGrainType(entity.getGrainType().toString());
        dto.setPlantDate(entity.getPlantDate().format(dateFormat.getDateFormat()));
        dto.setExpectedHarvestDate(entity.getExpectedHarvestDate().format(dateFormat.getDateFormat()));
        dto.setHumidity(entity.getWeatherInfo().getHumidity());
        dto.setWindSpeed(entity.getWeatherInfo().getWindSpeed());
        dto.setFieldName(entity.getFieldName());
        log.debug("Mapped FieldDto result: {}", dto);
        return dto;
    }

    public FieldInfoEntity dtoToInfo(FieldInfoCreateDto dto, FieldEntity entityToBind, WeatherInfoEntity weatherInfoToBind) {
        log.debug("Mapping FieldInfoCreateDto to FieldInfoEntity: {}, {}, {}", dto, entityToBind, weatherInfoToBind);
        FieldInfoEntity result = FieldInfoEntity.builder()
                .fieldId(entityToBind.getFieldId())
                .field(entityToBind)
                .surfaceArea(dto.getSurfaceArea())
                .grainType(Grain.valueOf(dto.getGrainType()))
                .plantDate(LocalDate.parse(dto.getPlantDate(), dateFormat.getDateFormat()))
                .expectedHarvestDate(LocalDate.parse(dto.getExpectedHarvestDate(), dateFormat.getDateFormat()))
                .weatherInfo(weatherInfoToBind)
                .fieldName(dto.getFieldName())
                .build();
        log.debug("Mapped FieldInfoEntity: {}", result);
        return result;
    }
}
