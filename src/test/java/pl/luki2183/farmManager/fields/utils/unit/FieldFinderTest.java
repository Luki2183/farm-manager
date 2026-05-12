package pl.luki2183.farmManager.fields.utils.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.luki2183.farmManager.exception.model.FieldEntityNotFoundException;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.fields.repo.FieldRepository;
import pl.luki2183.farmManager.fields.utils.FieldFinder;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FieldFinderTest {

    @Mock
    private FieldRepository repository;

    @InjectMocks
    private FieldFinder sut;

    @Test
    void find_should_return_entity_when_exists() {
        // given
        FieldEntity entity = FieldEntity.builder()
                .fieldId("field-1")
                .build();
        // and
        when(repository.findByFieldId("field-1")).thenReturn(Optional.of(entity));

        // when
        FieldEntity result = sut.find("field-1");

        // then
        Assertions.assertThat(result).isEqualTo(entity);
    }

    @Test
    void find_should_throw_FieldEntityNotFoundException_when_not_exists() {
        // given
        when(repository.findByFieldId("field-1")).thenReturn(Optional.empty());

        // when & then
        Assertions.assertThatThrownBy(() -> sut.find("field-1"))
                .isInstanceOf(FieldEntityNotFoundException.class);
    }

    @Test
    void exists_should_return_true_when_exists() {
        // given
        when(repository.existsByFieldId("field-1")).thenReturn(true);

        // when
        Boolean result = sut.exists("field-1");

        // then
        Assertions.assertThat(result).isEqualTo(true);
    }

    @Test
    void exists_should_return_false_when_not_exists() {
        // given
        when(repository.existsByFieldId("field-1")).thenReturn(false);

        // when
        Boolean result = sut.exists("field-1");

        // then
        Assertions.assertThat(result).isEqualTo(false);
    }
}