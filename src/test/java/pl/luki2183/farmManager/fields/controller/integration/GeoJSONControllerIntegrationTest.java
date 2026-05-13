package pl.luki2183.farmManager.fields.controller.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.model.Grain;
import pl.luki2183.farmManager.fieldInfo.repo.FieldInfoRepository;
import pl.luki2183.farmManager.fields.fixtures.FieldEntityFixtures;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.fields.repo.FieldRepository;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class GeoJSONControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private FieldInfoRepository infoRepository;

    private final String BASE_URL = "/api/geojsons";

    private void saveField(String fieldId) {
        FieldEntity fieldEntity = FieldEntityFixtures.withFieldId(fieldId).build();
        fieldRepository.save(fieldEntity);
        FieldInfoEntity infoEntity = FieldInfoEntity.builder()
                .field(fieldEntity)
                .fieldId(fieldId)
                .grainType(Grain.CARROT)
                .build();
        infoRepository.save(infoEntity);
        fieldEntity.setFieldInfo(infoEntity);
        fieldRepository.save(fieldEntity);
    }

    @Nested
    @DisplayName("GET /api/geojsons")
    class GetGeoJSONs {
        @Test
        void should_return_200_and_empty_list_when_no_fields_exist() throws Exception {
            // when & then
            mockMvc.perform(get(BASE_URL))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.count").value(0));
        }
        @Test
        void should_return_200_and_body_with_all_geojsons_from_fields() throws Exception {
            // given
            saveField("field-1");
            saveField("field-2");

            // when & then
            mockMvc.perform(get(BASE_URL))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.count").value(2))
                    .andExpect(jsonPath("$.dtoList[*].id", containsInAnyOrder("field-1", "field-2")));
        }
    }
}