package pl.luki2183.farmManager.fields.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.fields.dto.PointDto;
import pl.luki2183.farmManager.fields.model.PointEntity;
import pl.luki2183.farmManager.utils.Mapper;

/**
 * Mapper component implementing {@link pl.luki2183.farmManager.utils.Mapper} for
 * {@link PointEntity} and {@link PointDto}, providing bidirectional conversion
 * between the two types.
 */
@Slf4j
@Component
public class PointMapper implements Mapper<PointEntity, PointDto> {
    /**
     * Converts a {@link PointDto} into a {@link PointEntity}.
     *
     * @param dto the source point DTO
     * @return the corresponding {@link PointEntity}
     */
    @Override
    public PointEntity fromDtoToEntity(PointDto dto) {
        log.debug("Mapping PointDto to PointEntity: {}", dto);
        PointEntity result = new PointEntity();
        result.setLat(dto.getLat());
        result.setLng(dto.getLng());
        log.debug("Mapped PointEntity result: {}", result);
        return result;
    }

    /**
     * Converts a {@link PointEntity} into a {@link PointDto}.
     *
     * @param entity the source point entity
     * @return the corresponding {@link PointDto}
     */
    @Override
    public PointDto fromEntityToDto(PointEntity entity) {
        log.debug("Mapping PointEntity to PointDto: {}", entity);
        PointDto result = new PointDto();
        result.setLat(entity.getLat());
        result.setLng(entity.getLng());
        log.debug("Mapped PointDto result: {}", result);
        return result;
    }
}
