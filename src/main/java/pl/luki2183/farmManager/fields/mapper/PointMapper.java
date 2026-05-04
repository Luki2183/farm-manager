package pl.luki2183.farmManager.fields.mapper;

import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.fields.dto.PointDto;
import pl.luki2183.farmManager.fields.model.PointEntity;

@Component
public class PointMapper {
    public PointEntity fromDto(PointDto dto) {
        PointEntity pointEntity = new PointEntity();
        pointEntity.setLat(dto.getLat());
        pointEntity.setLng(dto.getLng());
        return pointEntity;
    }

    public PointDto toDto(PointEntity entity) {
        PointDto dto = new PointDto();
        dto.setLat(entity.getLat());
        dto.setLng(entity.getLng());
        return dto;
    }
}
