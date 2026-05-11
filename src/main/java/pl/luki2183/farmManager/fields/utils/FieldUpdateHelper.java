package pl.luki2183.farmManager.fields.utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.fields.dto.FieldDto;
import pl.luki2183.farmManager.fields.dto.GeoJSONDto;
import pl.luki2183.farmManager.fields.mapper.FieldMapper;
import pl.luki2183.farmManager.fields.model.FieldEntity;

/**
 * Helper component that applies a {@link GeoJSONDto} update to an existing
 * {@link FieldEntity} and returns the result as a {@link FieldDto}.
 *
 * <p>Extracted from {@link pl.luki2183.farmManager.fields.service.FieldPutService FieldPutService}
 * to keep update logic reusable and independently testable.</p>
 */
@Slf4j
@Component
@AllArgsConstructor
public class FieldUpdateHelper {

    private final FieldMapper mapper;

    /**
     * Applies the coordinate data from {@code dto} onto the provided
     * {@code updated} entity and returns the result as a {@link FieldDto}.
     *
     * @param updated the existing {@link FieldEntity} to modify
     * @param dto     the {@link GeoJSONDto} containing the new geometry
     * @return the updated field as a {@link FieldDto}
     */
    public FieldDto update(FieldEntity updated, GeoJSONDto dto) {
        log.debug("Entering update with entity: {}, and data: {}", updated, dto);
        FieldEntity entity = mapper.geoJSONDtoToFieldEntity(dto);
        updated.setCoordinates(entity.getCoordinates());
        FieldDto result = mapper.fieldToDto(updated);
        log.debug("Updated FieldEntity result: {}", result);
        return result;
    }
}
