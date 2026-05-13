package pl.luki2183.farmManager.fields.controller.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.luki2183.farmManager.exception.model.FieldEntityNotFoundException;
import pl.luki2183.farmManager.exception.model.PrimaryKeyViolationException;
import pl.luki2183.farmManager.fields.controller.FieldController;
import pl.luki2183.farmManager.fields.dto.FieldDto;
import pl.luki2183.farmManager.fields.dto.FieldListDto;
import pl.luki2183.farmManager.fields.dto.GeoJSONDto;
import pl.luki2183.farmManager.fields.fixtures.GeoJSONDtoFixtures;
import pl.luki2183.farmManager.fields.service.FieldDeleteService;
import pl.luki2183.farmManager.fields.service.FieldGetService;
import pl.luki2183.farmManager.fields.service.FieldPostService;
import pl.luki2183.farmManager.fields.service.FieldPutService;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FieldController.class)
class FieldControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private FieldGetService getService;

    @MockitoBean
    private FieldPostService postService;

    @MockitoBean
    private FieldDeleteService deleteService;

    @MockitoBean
    private FieldPutService putService;

    @Nested
    @DisplayName("GET /api/fields")
    class GetAllFields {
        @Test
        void getAllFields_should_return_200_with_body() throws Exception {
            // given
            FieldListDto fieldListDto = FieldListDto.builder()
                    .dtoList(List.of())
                    .count(0)
                    .build();
            // and
            when(getService.getAllFields()).thenReturn(fieldListDto);

            // when & then
            mockMvc.perform(get("/api/fields"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.count").value(0));
        }
    }

    @Nested
    @DisplayName("GET /api/fields/{fieldId}")
    class GetFieldWithId {
        @Test
        void getFieldWithId_should_return_404_when_not_found() throws Exception {
            // given
            String fieldId = "missing";
            when(getService.getFieldWithId(fieldId)).thenThrow(FieldEntityNotFoundException.class);

            // when & then
            mockMvc.perform(get("/api/fields/missing"))
                    .andExpect(status().isNotFound());
        }

        @Test
        void getFieldWithId_should_return_200_with_body() throws Exception {
            // given
            FieldDto fieldDto = FieldDto.builder()
                    .id("field-1")
                    .coordinates(List.of())
                    .build();
            when(getService.getFieldWithId("field-1")).thenReturn(fieldDto);

            // when & then
            mockMvc.perform(get("/api/fields/field-1"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value("field-1"));
        }
        }

    @Nested
    @DisplayName("GET /api/fields/exists/{fieldId}")
    class FieldExists {
        @Test
        void fieldExists_should_return_200_with_body_true_when_exists() throws Exception {
            // given
            String fieldId = "field-1";
            when(getService.checkIfExistsByFieldId(fieldId)).thenReturn(true);

            // when & then
            mockMvc.perform(get("/api/fields/exists/field-1"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").value(true));
        }

        @Test
        void fieldExists_should_return_200_with_body_false_when_not_exists() throws Exception {
            // given
            String fieldId = "missing";
            when(getService.checkIfExistsByFieldId(fieldId)).thenReturn(false);

            // when & then
            mockMvc.perform(get("/api/fields/exists/missing"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").value(false));
        }
    }

    @Nested
    @DisplayName("POST /api/fields")
    class AddField {
        @Test
        void addField_should_return_201_with_location_header_and_body() throws Exception {
            // given
            GeoJSONDto geoJSONDto = GeoJSONDtoFixtures.withFieldId("field-1").build();
            FieldDto result = FieldDto.builder().id("field-1").build();
            when(postService.saveField(any(GeoJSONDto.class))).thenReturn(result);

            // when & then
            mockMvc.perform(post("/api/fields")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(geoJSONDto)))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", "/api/fields/field-1"))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value("field-1"));
        }

        @Test
        void addField_should_return_409_when_PrimaryKeyViolationException_is_thrown() throws Exception {
            // given
            GeoJSONDto geoJSONDto = GeoJSONDtoFixtures.valid().build();
            when(postService.saveField(any())).thenThrow(PrimaryKeyViolationException.class);

            // when & then
            mockMvc.perform(post("/api/fields")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(geoJSONDto)))
                    .andExpect(status().isConflict());
        }

        @Test
        void should_return_400_when_request_body_field_id_is_blank_or_null() throws Exception {
            // given
            GeoJSONDto geoJSONDto = GeoJSONDtoFixtures.withFieldId("").build();

            // when & then
            mockMvc.perform(post("/api/fields")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(geoJSONDto)))
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(postService);
        }

        @Test
        void should_return_400_when_request_body_has_null_geometry() throws Exception {
            // given
            GeoJSONDto geoJSONDto = GeoJSONDtoFixtures.valid().geometry(null).build();

            // when & then
            mockMvc.perform(post("/api/fields")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(geoJSONDto)))
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(postService);
        }
    }

    @Nested
    @DisplayName("PUT /api/fields/{fieldId}")
    class UpdateField {
        @Test
        void updateField_should_return_200_with_body() throws Exception {
            // given
            String fieldId = "field-1";
            GeoJSONDto geoJSONDto = GeoJSONDtoFixtures.withFieldId("field-1").build();
            FieldDto result = FieldDto.builder().id("field-1").build();
            // and
            when(putService.updateField(any(String.class), any(GeoJSONDto.class))).thenReturn(result);

            // when & then
            mockMvc.perform(put("/api/fields/field-1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(geoJSONDto)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value("field-1"));
        }

        @Test
        void updateField_should_return_404_when_not_found() throws Exception {
            // given
            String fieldId = "missing";
            GeoJSONDto geoJSONDto = GeoJSONDtoFixtures.withFieldId("missing").build();
            // and
            when(putService.updateField(any(), any())).thenThrow(FieldEntityNotFoundException.class);

            // when & then
            mockMvc.perform(put("/api/fields/missing")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(geoJSONDto)))
                    .andExpect(status().isNotFound());
        }

        @Test
        void should_return_400_when_request_body_field_id_is_null_or_blank() throws Exception {
            // given
            GeoJSONDto geoJSONDto = GeoJSONDtoFixtures.withFieldId("").build();

            // when & then
            mockMvc.perform(put("/api/fields/field-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(geoJSONDto)))
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(putService);
        }

        @Test
        void should_return_400_when_request_body_geometry_is_null() throws Exception {
            // given
            GeoJSONDto geoJSONDto = GeoJSONDtoFixtures.valid().geometry(null).build();

            // when & then
            mockMvc.perform(put("/api/fields/field-1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(geoJSONDto)))
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(putService);
        }
    }

    @Nested
    @DisplayName("DELETE /api/fields/{fieldId}")
    class DeleteField {
        @Test
        void deleteField_should_return_204_when_successfully_deletes() throws Exception {
            // when & then
            mockMvc.perform(delete("/api/fields/field-1"))
                    .andExpect(status().isNoContent());

            verify(deleteService).deleteFieldById("field-1");
        }

        @Test
        void deleteField_should_return_404_when_not_found() throws Exception {
            // given
            doThrow(FieldEntityNotFoundException.class)
                    .when(deleteService).deleteFieldById("missing");

            // when & then
            mockMvc.perform(delete("/api/fields/missing"))
                    .andExpect(status().isNotFound());
        }
    }
}