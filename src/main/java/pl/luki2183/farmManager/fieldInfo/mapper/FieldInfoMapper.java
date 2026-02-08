package pl.luki2183.farmManager.fieldInfo.mapper;

import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoDto;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;

import java.util.List;

@Component
public class FieldInfoMapper {
    public List<FieldInfoDto> infoToDtoList(List<FieldInfoEntity> all) {
        return all.stream()
                .map(this::infoToDto)
                .toList();
    }

    private FieldInfoDto infoToDto(FieldInfoEntity entity) {
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
}
