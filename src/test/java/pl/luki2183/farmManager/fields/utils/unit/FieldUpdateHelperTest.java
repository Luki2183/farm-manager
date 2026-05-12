package pl.luki2183.farmManager.fields.utils.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.luki2183.farmManager.fields.dto.FieldDto;
import pl.luki2183.farmManager.fields.dto.GeoJSONDto;
import pl.luki2183.farmManager.fields.mapper.FieldMapper;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.fields.model.PointEntity;
import pl.luki2183.farmManager.fields.utils.FieldUpdateHelper;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FieldUpdateHelperTest {

    @Mock
    private FieldMapper mapper;

    @InjectMocks
    private FieldUpdateHelper sut;

    @Test
    void should_correctly_update_field_entity_with_new_coordinates() {
        // given
        GeoJSONDto geoJSONDto = GeoJSONDto.builder().build();
        List<PointEntity> newCoordinates = List.of(PointEntity.builder().lng(2).lat(2).build());
        FieldEntity mappedEntity = FieldEntity.builder()
                .coordinates(newCoordinates)
                .build();
        FieldEntity existingEntity = FieldEntity.builder()
                .coordinates(List.of(PointEntity.builder().lng(1).lat(1).build()))
                .build();
        FieldDto expectedDto = FieldDto.builder().build();
        // and
        when(mapper.geoJSONDtoToFieldEntity(geoJSONDto))
                .thenReturn(mappedEntity);
        when(mapper.fieldToDto(existingEntity))
                .thenReturn(expectedDto);

        // when
        FieldDto result = sut.update(existingEntity, geoJSONDto);

        // then
        Assertions.assertThat(result.getCoordinates()).isEqualTo(expectedDto.getCoordinates());
        Assertions.assertThat(existingEntity.getCoordinates()).isEqualTo(newCoordinates);
        verify(mapper).geoJSONDtoToFieldEntity(geoJSONDto);
        verify(mapper).fieldToDto(existingEntity);
    }

}