package pl.luki2183.farmManager.settings.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.fields.dto.PointDto;
import pl.luki2183.farmManager.fields.model.PointEntity;
import pl.luki2183.farmManager.settings.dto.SettingsDto;
import pl.luki2183.farmManager.settings.model.SettingsEntity;
import pl.luki2183.farmManager.utils.Mapper;

/**
 * Mapper component implementing {@link pl.luki2183.farmManager.utils.Mapper} for
 * {@link SettingsEntity} and {@link SettingsDto}, providing bidirectional conversion
 * between the two types.
 */
@Slf4j
@Component
public class SettingsMapper implements Mapper<SettingsEntity, SettingsDto> {

    /**
     * Converts a {@link SettingsEntity} into a {@link SettingsDto}.
     *
     * @param entity the source settings entity
     * @return the mapped {@link SettingsDto}
     */
    @Override
    public SettingsDto fromEntityToDto(SettingsEntity entity) {
        log.debug("Mapping SettingsEntity to SettingsDto: {}", entity);
        SettingsDto dto = new SettingsDto();
        PointDto centerDto = new PointDto();
        centerDto.setLng(entity.getCenter().getLng());
        centerDto.setLat(entity.getCenter().getLat());
        dto.setCenter(centerDto);
        dto.setGrainColors(entity.getGrainColors());
        log.debug("Mapped SettingsDto result: {}", dto);
        return dto;
    }

    /**
     * Converts a {@link SettingsDto} into a {@link SettingsEntity}.
     *
     * @param dto the source settings DTO
     * @return the mapped {@link SettingsEntity}
     */
    @Override
    public SettingsEntity fromDtoToEntity(SettingsDto dto) {
        log.debug("Mapping SettingsDto to SettingsEntity: {}", dto);
        SettingsEntity entity = new SettingsEntity();
        PointEntity centerEntity = new PointEntity();
        centerEntity.setLat(dto.getCenter().getLat());
        centerEntity.setLng(dto.getCenter().getLng());
        entity.setCenter(centerEntity);
        entity.setGrainColors(dto.getGrainColors());
        log.debug("Mapped SettingsEntity result: {}", entity);
        return entity;
    }
}
