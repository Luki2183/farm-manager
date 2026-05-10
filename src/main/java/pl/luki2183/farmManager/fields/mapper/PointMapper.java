package pl.luki2183.farmManager.fields.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.fields.dto.PointDto;
import pl.luki2183.farmManager.fields.model.PointEntity;

@Slf4j
@Component
public class PointMapper {
    public PointEntity fromDto(PointDto dto) {
        log.debug("Entering PointMapper.fromDto method with params: {}", dto);
        PointEntity pointEntity = new PointEntity();
        pointEntity.setLat(dto.getLat());
        pointEntity.setLng(dto.getLng());
        return pointEntity;
    }

    public PointDto toDto(PointEntity entity) {
        log.debug("Entering PointMapper.toDto method with params: {}", entity);
        PointDto dto = new PointDto();
        dto.setLat(entity.getLat());
        dto.setLng(entity.getLng());
        return dto;
    }
}
