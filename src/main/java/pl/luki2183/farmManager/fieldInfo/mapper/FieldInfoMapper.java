package pl.luki2183.farmManager.fieldInfo.mapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoCreateDto;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoDto;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoListDto;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.model.Grain;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.utils.DateFormat;
import pl.luki2183.farmManager.weatherInfo.model.WeatherInfoEntity;

import java.time.LocalDate;
import java.util.List;

/**
 * Mapper component providing conversions between {@link FieldInfoEntity}
 * and its various DTO representations ({@link FieldInfoDto}, {@link FieldInfoCreateDto}).
 *
 * <p>Uses {@link pl.luki2183.farmManager.utils.DateFormat} for consistent
 * {@code yyyy-MM-dd} date formatting and parsing across all mapping operations.</p>
 */
@Slf4j
@Component
@AllArgsConstructor
public class FieldInfoMapper {

    private final DateFormat dateFormat;

    /**
     * Converts a list of {@link FieldInfoEntity} objects into a {@link FieldInfoListDto}.
     *
     * @param all the list of field info entities to convert
     * @return a {@link FieldInfoListDto} containing the mapped DTOs and their count
     */
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

    /**
     * Converts a single {@link FieldInfoEntity} into a {@link FieldInfoDto}.
     *
     * <p>Surface area is rounded to two decimal places. Dates are formatted
     * using the shared {@link pl.luki2183.farmManager.utils.DateFormat} pattern.</p>
     *
     * @param entity the field info entity to convert
     * @return the corresponding {@link FieldInfoDto}
     */
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

    /**
     * Constructs a {@link FieldInfoEntity} from a {@link FieldInfoCreateDto},
     * a resolved {@link FieldEntity}, and a fetched {@link WeatherInfoEntity}.
     *
     * @param dto              the creation DTO containing agronomic data
     * @param entityToBind     the {@link FieldEntity} this info record belongs to
     * @param weatherInfoToBind the current weather data to associate with the field
     * @return the constructed {@link FieldInfoEntity}
     */
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
