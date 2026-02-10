package pl.luki2183.farmManager.fieldInfo.mapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoDto;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.model.Grain;
import pl.luki2183.farmManager.fieldInfo.model.WeatherInfoEntity;
import pl.luki2183.farmManager.fieldInfo.service.WeatherGetService;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.fields.repo.FieldRepository;
import pl.luki2183.farmManager.fields.service.FieldGetService;

import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class FieldInfoMapper {

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
        dto.setPlantDate(entity.getPlantDate().format(dateFormat));
        dto.setExpectedHarvestDate(entity.getExpectedHarvestDate().format(dateFormat));
        dto.setHumidity(entity.getWeatherInfo().getHumidity());
        dto.setWindSpeed(entity.getWeatherInfo().getWindSpeed());
        dto.setFieldColor(String.valueOf(entity.getFieldColor().getRGB()));

        return dto;
    }

    public FieldInfoEntity dtoToInfo(FieldInfoDto dto, FieldEntity entityToBind, WeatherInfoEntity weatherInfoToBind) {
//        todo expectedHarvestDate = automatic update with internal logic
        return FieldInfoEntity.builder()
                .fieldId(entityToBind.getId())
                .field(entityToBind)
                .surfaceArea(dto.getSurfaceArea())
                .grainType(Grain.valueOf(dto.getGrainType()))
                .plantDate(LocalDate.parse(dto.getPlantDate(), dateFormat))
                .expectedHarvestDate(LocalDate.parse(dto.getExpectedHarvestDate(), dateFormat))
                .weatherInfo(weatherInfoToBind)
                .fieldColor(Color.decode(dto.getFieldColor()))
                .build();
    }
}
