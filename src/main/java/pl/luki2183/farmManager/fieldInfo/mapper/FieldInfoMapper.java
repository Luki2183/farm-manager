package pl.luki2183.farmManager.fieldInfo.mapper;

import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoDto;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fields.model.FieldEntity;

import java.util.List;

@Component
public class FieldInfoMapper {
    public List<FieldInfoDto> infoToDtoList(List<FieldInfoEntity> all) {
        return all.stream()
                .map(this::infoToDto)
                .toList();
    }

    public FieldInfoDto infoToDto(FieldInfoEntity entity) {
        return FieldInfoDto.builder()
                .fieldId(entity.getFieldId())
                .surfaceArea(entity.getSurfaceArea())
                .grainType(entity.getGrainType())
                .plantDate(entity.getPlantDate())
                .expectedHarvestDate(entity.getExpectedHarvestDate())
                .humidity(entity.getHumidity())
                .windSpeed(entity.getWindSpeed())
                .fieldColor(entity.getFieldColor())
                .build();
    }

    public FieldInfoEntity dtoToInfo(FieldInfoDto dto) {
//        todo expectedHarvestDate = automatic update with internal logic
//        todo humidity, windspeed get from external api
        return FieldInfoEntity.builder()
                .fieldId(dto.getFieldId())
                .surfaceArea(dto.getSurfaceArea())
                .grainType(dto.getGrainType())
                .plantDate(dto.getPlantDate())
                .expectedHarvestDate(dto.getExpectedHarvestDate())
                .humidity(dto.getHumidity())
                .windSpeed(dto.getWindSpeed())
                .fieldColor(dto.getFieldColor())
                .build();
    }
}
