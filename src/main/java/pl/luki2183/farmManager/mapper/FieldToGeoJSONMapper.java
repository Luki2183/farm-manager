package pl.luki2183.farmManager.mapper;

import pl.luki2183.farmManager.dto.GeoJSONDto;
import pl.luki2183.farmManager.dto.PointDto;
import pl.luki2183.farmManager.entity.FieldEntity;

import java.util.List;

public class FieldToGeoJSONMapper {
    public static List<GeoJSONDto> convert(List<FieldEntity> fields) {
        return fields.stream()
                .map(fieldEntity -> {
                    GeoJSONDto result = new GeoJSONDto();
                    result.setId(fieldEntity.getId());
                    GeoJSONDto.Geometry geometry = new GeoJSONDto.Geometry();
                    List<double[]> points = fieldEntity.getCoordinates().stream().map(pointEntity -> {
                        double[] array = new double[2];
                        array[0] = pointEntity.getLat();
                        array[1] = pointEntity.getLng();
                        return array;
                    }).toList();
                    geometry.setCoordinates(List.of(points));
                    result.setGeometry(geometry);
                    return result;
                }).toList();
    }
}
