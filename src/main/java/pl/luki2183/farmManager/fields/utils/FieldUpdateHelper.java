package pl.luki2183.farmManager.fields.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.fields.dto.GeoJSONDto;
import pl.luki2183.farmManager.fields.mapper.FieldMapper;
import pl.luki2183.farmManager.fields.model.FieldEntity;

@Component
@AllArgsConstructor
public class FieldUpdateHelper {

    private final FieldMapper mapper;

    public FieldEntity update(FieldEntity updated, GeoJSONDto dto) {
        FieldEntity entity = mapper.geoJSONDtoToFieldEntity(dto);
        updated.setCoordinates(entity.getCoordinates());
        return updated;
    }
}
