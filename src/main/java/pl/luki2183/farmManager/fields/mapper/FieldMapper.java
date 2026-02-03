package pl.luki2183.farmManager.fields.mapper;

import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.fields.dto.FieldDto;
import pl.luki2183.farmManager.fields.dto.GeoJSONDto;
import pl.luki2183.farmManager.fields.dto.PointDto;
import pl.luki2183.farmManager.fields.entity.FieldEntity;
import pl.luki2183.farmManager.fields.entity.PointEntity;

import java.util.List;

@Component
public class FieldMapper {
    public List<GeoJSONDto> fieldsToGeoJSONDtoList(List<FieldEntity> fields) {
        return fields.stream()
                .map(this::fieldToGeoJSON).toList();
    }

    public GeoJSONDto fieldToGeoJSON(FieldEntity field) {
        GeoJSONDto result = new GeoJSONDto();
        result.setId(field.getId());
        GeoJSONDto.Geometry geometry = new GeoJSONDto.Geometry();
        List<double[]> points = field.getCoordinates().stream().map(pointEntity -> {
            double[] array = new double[2];
            array[0] = pointEntity.getLat();
            array[1] = pointEntity.getLng();
            return array;
        }).toList();
        geometry.setCoordinates(List.of(points));
        result.setGeometry(geometry);
        return result;
    }

    public List<FieldDto> fieldToDtoList(List<FieldEntity> fields) {
        return fields.stream()
                .map(this::fieldToDto).toList();
    }

    private FieldDto fieldToDto(FieldEntity fieldEntity) {
        FieldDto result = new FieldDto();
        result.setId(fieldEntity.getId());
        result.setCoordinates(
                fieldEntity.getCoordinates().stream().map(pointEntity -> {
                    PointDto pointDto = new PointDto();
                    pointDto.setLng(pointEntity.getLng());
                    pointDto.setLat(pointEntity.getLat());
                    return pointDto;
                }).toList()
        );
        result.setArea(fieldEntity.getArea());
        return result;
    }

    public FieldEntity geoJSONDtoToFieldEntity(GeoJSONDto dto) {
        FieldEntity entity = new FieldEntity();

        entity.setId(dto.getId());

        entity.setCoordinates(
                dto.getGeometry().getCoordinates().getFirst().stream()
                        .map(doubles -> {
                            PointEntity result = new PointEntity();
                            result.setLat(doubles[0]);
                            result.setLng(doubles[1]);
                            return result;
                        }).toList()
        );

        entity.setArea(dto.getArea());

        return entity;
    }
}
