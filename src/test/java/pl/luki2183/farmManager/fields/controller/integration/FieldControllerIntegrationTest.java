package pl.luki2183.farmManager.fields.controller.integration;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.luki2183.farmManager.fields.dto.GeoJSONDto;
import pl.luki2183.farmManager.fields.fixtures.FieldEntityFixtures;
import pl.luki2183.farmManager.fields.fixtures.GeoJSONDtoFixtures;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.fields.repo.FieldRepository;
import tools.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class FieldControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private FieldRepository fieldRepository;

    private static final String BASE_URL = "/api/fields";
    private static final String FIELD_ID = "field-1";

    private FieldEntity saveField(String fieldId) {
        FieldEntity entity = FieldEntityFixtures.withFieldId(fieldId).build();
        return fieldRepository.save(entity);
    }

    @Nested
    @DisplayName("GET /api/fields")
    class GetAllFields {
        @Test
        void should_return_200_and_empty_list_when_no_fields_exist() throws Exception {
            // when & then
            mockMvc.perform(get(BASE_URL))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.dtoList", hasSize(0)));
        }

        @Test
        void should_return_200_and_all_fields() throws Exception {
            // given
            saveField(FIELD_ID);
            saveField("field-2");

            // when & then
            mockMvc.perform(get(BASE_URL))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.dtoList", hasSize(2)))
                    .andExpect(jsonPath("$.dtoList[*].id", containsInAnyOrder(FIELD_ID, "field-2")));
        }
    }

    @Nested
    @DisplayName("GET /api/fields/{fieldId}")
    class GetFieldWithId {
        @Test
        void should_return_404_when_not_found() throws Exception {
            // when & then
            mockMvc.perform(get(BASE_URL.concat("/missing")))
                    .andExpect(status().isNotFound());
        }

        @Test
        void should_return_200_and_body_with_field() throws Exception {
            // given
            saveField(FIELD_ID);

            // when & then
            mockMvc.perform(get(BASE_URL.concat("/").concat(FIELD_ID)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(FIELD_ID));
        }
        @Test
        void should_return_400_when_path_variable_is_missing() throws Exception {
            // when & then
            mockMvc.perform(get(BASE_URL.concat("/".concat(" "))))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("GET /api/fields/exists/{fieldId}")
    class FieldExists {
        @Test
        void should_return_200_and_true_when_field_exists() throws Exception {
            // given
            saveField(FIELD_ID);

            // when & then
            mockMvc.perform(get(BASE_URL.concat("/exists/").concat(FIELD_ID)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").value(true));
        }

        @Test
        void should_return_200_and_false_when_field_dont_exist() throws Exception {
            // when & then
            mockMvc.perform(get(BASE_URL.concat("/exists/").concat("missing")))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").value(false));
        }
        @Test
        void should_return_400_when_path_variable_is_missing() throws Exception {
            // when & then
            mockMvc.perform(get(BASE_URL.concat("/exists/").concat(" ")))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("POST /api/fields")
    class AddField {
        @Test
        void should_return_409_when_field_with_same_id_exists() throws Exception {
            // given
            saveField(FIELD_ID);
            GeoJSONDto geoJSONDto = GeoJSONDtoFixtures.withFieldId(FIELD_ID).build();

            // when & then
            mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(geoJSONDto)))
                    .andExpect(status().isConflict())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }
        @Test
        void should_return_201_with_body_when_field_created() throws Exception {
            // given
            GeoJSONDto geoJSONDto = GeoJSONDtoFixtures.withFieldId(FIELD_ID).build();

            // when & then
            mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(geoJSONDto)))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", "/api/fields/" + FIELD_ID))
                    .andExpect(jsonPath("$.id").value(FIELD_ID));
        }
        @Test
        void should_return_400_when_body_is_missing_field_id() throws Exception {
            // given
            GeoJSONDto geoJSONDto = GeoJSONDtoFixtures.withFieldId("").build();

            // when & then
            mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(geoJSONDto)))
                    .andExpect(status().isBadRequest());
        }
        @Test
        void should_return_400_when_body_is_missing_geometry() throws Exception {
            // given
            GeoJSONDto geoJSONDto = GeoJSONDtoFixtures.valid().geometry(null).build();

            // when & then
            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(geoJSONDto)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("PUT /api/fields/{fieldId}")
    class UpdateField {
        @Test
        void should_return_404_when_not_found() throws Exception {
            // given
            GeoJSONDto geoJSONDto = GeoJSONDtoFixtures.withFieldId("missing").build();

            // when & then
            mockMvc.perform(put(BASE_URL.concat("/missing"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(geoJSONDto)))
                    .andExpect(status().isNotFound());
        }
        @Test
        void should_return_200_when_updated() throws Exception {
            // given
            GeoJSONDto geoJSONDto = GeoJSONDtoFixtures.withFieldId(FIELD_ID).build();
            saveField(FIELD_ID);

            // when & then
            mockMvc.perform(put(BASE_URL.concat("/").concat(FIELD_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(geoJSONDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(FIELD_ID));
        }
        @Test
        void should_return_400_when_path_variable_is_missing() throws Exception {
            // given
            GeoJSONDto geoJSONDto = GeoJSONDtoFixtures.valid().build();
            // when & then
            mockMvc.perform(put(BASE_URL.concat("/").concat(" "))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(geoJSONDto)))
                    .andExpect(status().isBadRequest());
        }
        @Test
        void should_return_400_when_body_is_missing_field_id() throws Exception {
            // given
            GeoJSONDto geoJSONDto = GeoJSONDtoFixtures.withFieldId("").build();

            // when & then
            mockMvc.perform(put(BASE_URL.concat("/").concat("field-1"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(geoJSONDto)))
                    .andExpect(status().isBadRequest());
        }
        @Test
        void should_return_400_when_body_is_missing_geometry() throws Exception {
            // given
            GeoJSONDto geoJSONDto = GeoJSONDtoFixtures.valid().geometry(null).build();

            // when & then
            mockMvc.perform(put(BASE_URL.concat("/").concat("field-1"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(geoJSONDto)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("DELETE /api/fields/{fieldId}")
    class DeleteField {
        @Test
        void should_return_204_when_deleted_existing() throws Exception {
            // given
            saveField(FIELD_ID);

            // when & then
            mockMvc.perform(delete(BASE_URL.concat("/").concat(FIELD_ID)))
                    .andExpect(status().isNoContent());

            mockMvc.perform(get(BASE_URL.concat("/").concat(FIELD_ID)))
                    .andExpect(status().isNotFound());
        }
        @Test
        void should_return_404_when_not_found() throws Exception {
            // when & then
            mockMvc.perform(delete(BASE_URL.concat("/missing")))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }
        @Test
        void should_return_400_when_path_variable_is_missing() throws Exception {
            // when & then
            mockMvc.perform(delete(BASE_URL.concat("/").concat(" ")))
                    .andExpect(status().isBadRequest());
        }
    }
}