package pl.luki2183.farmManager.fields.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.fields.dto.*;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.fields.model.PointEntity;

import java.util.List;

/**
 * Mapper component providing conversions between {@link FieldEntity}
 * and its various DTO representations ({@link FieldDto}, {@link GeoJSONDto}).
 *
 * <p>GeoJSON coordinates follow the {@code [longitude, latitude]} convention
 * (RFC 7946), while {@link PointEntity} stores {@code lat} and {@code lng}
 * as separate fields. This mapper handles that axis swap transparently.</p>
 */
@Slf4j
@Component
public class FieldMapper {
    /**
     * Converts a list of {@link FieldEntity} objects into a {@link GeoJSONListDto}.
     *
     * @param fields the list of field entities to convert
     * @return a {@link GeoJSONListDto} containing the mapped features and their count
     */
    public GeoJSONListDto fieldsToGeoJSONDtoList(List<FieldEntity> fields) {
        log.debug("Mapping FieldEntity list to GeoJSONListDto: {}", fields);
        List<GeoJSONDto> geoJSONDtos = fields.stream()
                .map(this::fieldToGeoJSONDto).toList();
        GeoJSONListDto result = new GeoJSONListDto();
        result.setDtoList(geoJSONDtos);
        result.setCount(geoJSONDtos.size());
        log.debug("Mapped GeoJSONListDto result: {}", result);
        return result;
    }

    /**
     * Converts a single {@link FieldEntity} into a {@link GeoJSONDto}.
     *
     * <p>Coordinate pairs are emitted as {@code [longitude, latitude]}
     * per the GeoJSON specification.</p>
     *
     * @param field the field entity to convert
     * @return the corresponding {@link GeoJSONDto}
     */
    public GeoJSONDto fieldToGeoJSONDto(FieldEntity field) {
        log.debug("Mapping FieldEntity to GeoJSONDto: {}", field);
        GeoJSONDto result = new GeoJSONDto();
        result.setId(field.getFieldId());
        GeoJSONDto.Geometry geometry = new GeoJSONDto.Geometry();
        List<double[]> points = field.getCoordinates().stream().map(pointEntity -> {
            double[] array = new double[2];
            array[0] = pointEntity.getLng();
            array[1] = pointEntity.getLat();
            return array;
        }).toList();
        geometry.setCoordinates(List.of(points));
        result.setGeometry(geometry);
        result.setGrainType(field.getFieldInfo().getGrainType());
        log.debug("Mapped GeoJSONDto result: {}", result);
        return result;
    }

    /**
     * Converts a list of {@link FieldEntity} objects into a {@link FieldListDto}.
     *
     * @param fields the list of field entities to convert
     * @return a {@link FieldListDto} containing the mapped DTOs and their count
     */
    public FieldListDto fieldsToDtoList(List<FieldEntity> fields) {
        log.debug("Mapping FieldEntity list to FieldListDto: {}", fields);
        List<FieldDto> list = fields.stream()
                .map(this::fieldToDto).toList();
        FieldListDto result = new FieldListDto();
        result.setDtoList(list);
        result.setCount(list.size());
        log.debug("Mapped FieldListDto result: {}", result);
        return result;
    }

    /**
     * Converts a single {@link FieldEntity} into a {@link FieldDto}.
     *
     * @param fieldEntity the field entity to convert
     * @return the corresponding {@link FieldDto}
     */
    public FieldDto fieldToDto(FieldEntity fieldEntity) {
        log.debug("Mapping FieldEntity to FieldDto: {}", fieldEntity);
        FieldDto result = new FieldDto();
        result.setId(fieldEntity.getFieldId());
        result.setCoordinates(
                fieldEntity.getCoordinates().stream().map(pointEntity -> {
                    PointDto pointDto = new PointDto();
                    pointDto.setLng(pointEntity.getLng());
                    pointDto.setLat(pointEntity.getLat());
                    return pointDto;
                }).toList()
        );
        log.debug("Mapped FieldDto result: {}", result);
        return result;
    }

    /**
     * Converts a {@link GeoJSONDto} into a {@link FieldEntity}.
     *
     * <p>Expects coordinates in {@code [longitude, latitude]} order per the
     * GeoJSON specification; these are swapped into {@code lat}/{@code lng}
     * fields on the resulting {@link PointEntity} instances.</p>
     *
     * @param dto the GeoJSON feature DTO to convert
     * @return the corresponding {@link FieldEntity}
     */
    public FieldEntity geoJSONDtoToFieldEntity(GeoJSONDto dto) {
        log.debug("Mapping GeoJSONDto to FieldEntity: {}", dto);
        FieldEntity result = new FieldEntity();

        result.setFieldId(dto.getId());

        result.setCoordinates(
                dto.getGeometry().getCoordinates().getFirst().stream()
                        .map(doubles -> {
                            PointEntity pointEntity = new PointEntity();
                            pointEntity.setLat(doubles[1]);
                            pointEntity.setLng(doubles[0]);
                            return pointEntity;
                        }).toList()
        );
        log.debug("Mapped FieldEntity result: {}", result);
        return result;
    }
}
