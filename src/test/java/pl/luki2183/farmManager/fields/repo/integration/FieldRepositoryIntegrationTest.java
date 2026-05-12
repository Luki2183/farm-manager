package pl.luki2183.farmManager.fields.repo.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import pl.luki2183.farmManager.fields.fixtures.FieldEntityFixtures;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.fields.repo.FieldRepository;

import java.util.Optional;

@DataJpaTest
class FieldRepositoryIntegrationTest {

    @Autowired
    private FieldRepository sut;

    private FieldEntity saved;

    @BeforeEach
    void setUp() {
        // given
        saved = sut.save(
                FieldEntityFixtures.valid()
                    .fieldId("field-1")
                    .fieldInfo(null)
                    .build()
        );
    }

    @Test
    void findByFieldId_should_return_entity_when_exists() {
        // when
        Optional<FieldEntity> result = sut.findByFieldId("field-1");

        // then
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().getFieldId()).isEqualTo("field-1");
    }

    @Test
    void findByFieldId_should_return_empty_when_not_exists() {
        // when
        Optional<FieldEntity> result = sut.findByFieldId("missing");

        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    void existsByFieldId_should_return_true_when_exists() {
        // when
        boolean result = sut.existsByFieldId("field-1");

        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    void existsByFieldId_should_return_false_when_not_exists() {
        // when
        boolean result = sut.existsByFieldId("missing");

        // then
        Assertions.assertThat(result).isFalse();
    }
}