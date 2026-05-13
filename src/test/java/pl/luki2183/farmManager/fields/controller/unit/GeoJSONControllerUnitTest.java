package pl.luki2183.farmManager.fields.controller.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.luki2183.farmManager.fields.controller.GeoJSONController;
import pl.luki2183.farmManager.fields.dto.GeoJSONListDto;
import pl.luki2183.farmManager.fields.service.GeoJSONGetService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GeoJSONController.class)
class GeoJSONControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GeoJSONGetService getService;

    @Nested
    @DisplayName("GET /api/geojsons")
    class GetGeoJSONs {
        @Test
        void should_return_200_with_body() throws Exception {
            // given
            GeoJSONListDto geoJSONListDto = GeoJSONListDto.builder()
                    .dtoList(List.of())
                    .count(0)
                    .build();
            // and
            when(getService.getAllGeoJSONs()).thenReturn(geoJSONListDto);

            // when & then
            mockMvc.perform(get("/api/geojsons"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.count").value(0));
        }
    }
}