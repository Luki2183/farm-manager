package pl.luki2183.farmManager.fields.controller.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import pl.luki2183.farmManager.fieldInfo.model.Grain;
import pl.luki2183.farmManager.fields.controller.FieldsViewController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FieldsViewController.class)
class FieldsViewControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("GET /")
    class GetMap {
        @Test
        void should_return_200_and_map_view() throws Exception {
            // when & then
            mockMvc.perform(get("/"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("map"))
                    .andExpect(model().attribute("activePage", "map"))
                    .andExpect(model().attribute("grainTypes", Grain.values()));
        }

    }

    @Nested
    @DisplayName("GET /{fieldId}")
    class GetMapWithSelectedField {
        @Test
        void should_return_400_when_fieldId_is_null_or_blank() throws Exception {
            // when & then
            mockMvc.perform(get("/ "))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("GET /error")
    class GetError {
        @Test
        void should_return_200_and_error_view() {

        }
    }
}