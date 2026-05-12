package pl.luki2183.farmManager.fields.mapper.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.luki2183.farmManager.fields.dto.PointDto;
import pl.luki2183.farmManager.fields.mapper.PointMapper;
import pl.luki2183.farmManager.fields.model.PointEntity;

class PointMapperUnitTest {

    private final PointMapper sut = new PointMapper();

    @Test
    void fromDtoToEntity_should_map_correctly() {
        // given
        PointDto pointDto = PointDto.builder()
                .lng(21.0)
                .lat(51.0)
                .build();
        // when
        PointEntity result = sut.fromDtoToEntity(pointDto);

        // then
        Assertions.assertThat(result.getLat()).isEqualTo(51.0);
        Assertions.assertThat(result.getLng()).isEqualTo(21.0);
    }

    @Test
    void fromEntityToDto_should_map_correctly() {
        // given
        PointEntity pointEntity = PointEntity.builder()
                .lng(21.0)
                .lat(51.0)
                .build();

        // when
        PointDto result = sut.fromEntityToDto(pointEntity);

        // then
        Assertions.assertThat(result.getLng()).isEqualTo(21.0);
        Assertions.assertThat(result.getLat()).isEqualTo(51.0);
    }
}