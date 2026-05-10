package pl.luki2183.farmManager.settings.mapper;

import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.fields.dto.PointDto;
import pl.luki2183.farmManager.fields.model.PointEntity;
import pl.luki2183.farmManager.settings.dto.SettingsDto;
import pl.luki2183.farmManager.settings.model.SettingsEntity;

@Component
public class SettingsMapper {
    public SettingsDto mapToDto(SettingsEntity entity) {
        SettingsDto dto = new SettingsDto();
        PointDto centerDto = new PointDto();
        centerDto.setLng(entity.getCenter().getLng());
        centerDto.setLat(entity.getCenter().getLat());
        dto.setCenter(centerDto);
        dto.setColorMap(entity.getGrainColors());
        return dto;
    }

    public SettingsEntity mapToEntity(SettingsDto dto) {
        SettingsEntity entity = new SettingsEntity();
        PointEntity centerEntity = new PointEntity();
        centerEntity.setLat(dto.getCenter().getLat());
        centerEntity.setLng(dto.getCenter().getLng());
        entity.setCenter(centerEntity);
        entity.setGrainColors(dto.getColorMap());
        return entity;
    }
}
