package pl.luki2183.farmManager.fields.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.luki2183.farmManager.fieldInfo.model.Grain;

/**
 * MVC controller handling Thymeleaf view rendering for the map and error pages.
 *
 * <p>Populates the model with grain types and optional field focus data
 * before delegating to the {@code map} or {@code error} templates.</p>
 */
@Slf4j
@Controller
public class FieldsViewController {

    /**
     * Renders the main map view without a focused field.
     *
     * @param model the Spring MVC {@link Model} used to pass attributes to the view
     * @return the logical name of the map Thymeleaf template
     */
    @GetMapping("/")
    public String map(Model model) {
        log.info("Received request to render map view");
        model.addAttribute("activePage", "map");
        model.addAttribute("grainTypes", Grain.values());
        log.debug("Added grainTypes to model: {}", (Object) Grain.values());
        return "map";
    }

    /**
     * Renders the main map view with a specific field pre-focused at center.
     *
     * @param model   the Spring MVC {@link Model} used to pass attributes to the view
     * @param fieldId the business identifier of the field to focus on
     * @return the logical name of the map Thymeleaf template
     */
    @GetMapping("/{fieldId}")
    public String map(Model model, @PathVariable String fieldId) {
        log.info("Received request to render map view with fieldId: {}, at center", fieldId);
        model.addAttribute("activePage", "map");
        model.addAttribute("grainTypes", Grain.values());
        model.addAttribute("focusedFieldId", fieldId);
        log.debug("Added grainTypes, focusedFieldId to model: {}, {}", (Object) Grain.values(), fieldId);
        return "map";
    }

    /**
     * Renders the error page.
     *
     * @param model the Spring MVC {@link Model} used to pass attributes to the view
     * @return the logical name of the error Thymeleaf template
     */
    @GetMapping("/error")
    public String error(Model model) {
        log.warn("Received request to render error view");
        return "error";
    }
}
