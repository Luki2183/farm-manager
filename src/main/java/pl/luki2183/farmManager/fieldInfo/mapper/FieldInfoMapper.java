package pl.luki2183.farmManager.fieldInfo.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoDto;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.model.WeatherInfoEntity;
import pl.luki2183.farmManager.fieldInfo.service.WeatherGetService;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.fields.repo.FieldRepository;
import pl.luki2183.farmManager.fields.service.FieldGetService;

import java.util.List;

@Component
@AllArgsConstructor
public class FieldInfoMapper {

    private final WeatherGetService weatherGetService;
    private final FieldRepository fieldRepository;

    public List<FieldInfoDto> infoToDtoList(List<FieldInfoEntity> all) {
        return all.stream()
                .map(this::infoToDto)
                .toList();
    }

    public FieldInfoDto infoToDto(FieldInfoEntity entity) {
        return FieldInfoDto.builder()
                .fieldId(entity.getField().getId())
                .surfaceArea(entity.getSurfaceArea())
                .grainType(entity.getGrainType())
                .plantDate(entity.getPlantDate())
                .expectedHarvestDate(entity.getExpectedHarvestDate())
                .humidity(entity.getWeatherInfo().getHumidity())
                .windSpeed(entity.getWeatherInfo().getWindSpeed())
                .fieldColor(entity.getFieldColor())
                .build();
    }

    public FieldInfoEntity dtoToInfo(FieldInfoDto dto) {
//        todo expectedHarvestDate = automatic update with internal logic
        FieldEntity fieldEntity = fieldRepository.getReferenceById(dto.getFieldId());

        WeatherInfoEntity weatherInfo = weatherGetService.getWeatherInfo(fieldEntity.getCoordinates().getFirst());

        return FieldInfoEntity.builder()
                .field(fieldEntity)
                .surfaceArea(dto.getSurfaceArea())
                .grainType(dto.getGrainType())
                .plantDate(dto.getPlantDate())
                .expectedHarvestDate(dto.getExpectedHarvestDate())
                .weatherInfo(weatherInfo)
                .fieldColor(dto.getFieldColor())
                .build();
    }
}
