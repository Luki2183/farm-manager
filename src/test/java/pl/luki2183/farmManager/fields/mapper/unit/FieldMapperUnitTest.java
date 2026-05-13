package pl.luki2183.farmManager.fields.mapper.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.luki2183.farmManager.fieldInfo.model.Grain;
import pl.luki2183.farmManager.fields.dto.FieldDto;
import pl.luki2183.farmManager.fields.dto.FieldListDto;
import pl.luki2183.farmManager.fields.dto.GeoJSONDto;
import pl.luki2183.farmManager.fields.dto.GeoJSONListDto;
import pl.luki2183.farmManager.fields.fixtures.FieldEntityFixtures;
import pl.luki2183.farmManager.fields.fixtures.GeoJSONDtoFixtures;
import pl.luki2183.farmManager.fields.mapper.FieldMapper;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.fields.model.PointEntity;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FieldMapperUnitTest {

    @Spy
    private final FieldMapper sut = new FieldMapper();

    @Test
    void should_map_FieldEntity_to_FieldDto_correctly() {
        // given
        List<PointEntity> coords = List.of(PointEntity.builder().lat(1).lng(1).build());
        FieldEntity entity = FieldEntityFixtures.withFieldId("field-1")
                .coordinates(coords)
                .build();

        // when
        FieldDto result = sut.fieldToDto(entity);

        // then
        Assertions.assertThat(result.getId()).isEqualTo("field-1");
        Assertions.assertThat(result.getCoordinates().size()).isEqualTo(1);
        Assertions.assertThat(result.getCoordinates().getFirst().getLat()).isEqualTo(1.0);
        Assertions.assertThat(result.getCoordinates().getFirst().getLng()).isEqualTo(1.0);
    }

    @Test
    void should_map_FieldEntity_to_GeoJSONDto_correctly() {
        // given
        List<PointEntity> coords = List.of(PointEntity.builder().lat(1).lng(2).build());
        FieldEntity entity = FieldEntityFixtures.withFilledFieldInfoEntity()
                .fieldId("field-1")
                .coordinates(coords)
                .build();

        // when
        GeoJSONDto result = sut.fieldToGeoJSONDto(entity);

        // then
        Assertions.assertThat(result.getId()).isEqualTo("field-1");
        Assertions.assertThat(result.getGrainType()).isEqualTo(Grain.CARROT);
        Assertions.assertThat(result.getGeometry().getCoordinates().getFirst().size()).isEqualTo(1);
        Assertions.assertThat(result.getGeometry().getCoordinates().getFirst().getFirst()[0]).isEqualTo(2);
        Assertions.assertThat(result.getGeometry().getCoordinates().getFirst().getFirst()[1]).isEqualTo(1);
    }

    @Test
    void should_map_FieldEntity_list_to_GeoJSONListDto() {
        // given
        FieldEntity entity1 = FieldEntityFixtures.withFilledFieldInfoEntity().fieldId("field-1").build();
        FieldEntity entity2 = FieldEntityFixtures.withFilledFieldInfoEntity().fieldId("field-2").build();
        List<FieldEntity> fields = List.of(entity1, entity2);

        // when
        GeoJSONListDto result = sut.fieldsToGeoJSONDtoList(fields);

        // then
        Assertions.assertThat(result.getCount()).isEqualTo(2);
        Assertions.assertThat(result.getDtoList().size()).isEqualTo(2);
        verify(sut, times(2)).fieldToGeoJSONDto(any());
    }

    @Test
    void should_map_FieldEntity_list_to_FieldListDto() {
        // given
        FieldEntity entity1 = FieldEntityFixtures.withFieldId("field-1").build();
        FieldEntity entity2 = FieldEntityFixtures.withFieldId("field-2").build();
        List<FieldEntity> fields = List.of(entity1, entity2);

        // when
        FieldListDto result = sut.fieldsToDtoList(fields);

        // then
        Assertions.assertThat(result.getCount()).isEqualTo(2);
        Assertions.assertThat(result.getDtoList().size()).isEqualTo(2);
        verify(sut, times(2)).fieldToDto(any());
    }

    @Test
    void should_map_GeoJSONDto_to_FieldEntity() {
        // given
        GeoJSONDto geoJSONDto = GeoJSONDtoFixtures.valid().id("field-1").build();

        // when
        FieldEntity result = sut.geoJSONDtoToFieldEntity(geoJSONDto);

        // then
        Assertions.assertThat(result.getFieldId()).isEqualTo("field-1");
        Assertions.assertThat(result.getCoordinates().size()).isEqualTo(1);
        Assertions.assertThat(result.getCoordinates().getFirst().getLng()).isEqualTo(21.0);
        Assertions.assertThat(result.getCoordinates().getFirst().getLat()).isEqualTo(51.0);
    }
}