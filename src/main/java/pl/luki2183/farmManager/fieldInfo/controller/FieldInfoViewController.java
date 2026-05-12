package pl.luki2183.farmManager.fieldInfo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoListDto;
import pl.luki2183.farmManager.fieldInfo.model.Grain;
import pl.luki2183.farmManager.fieldInfo.service.FieldInfoGetService;

/**
 * MVC controller handling Thymeleaf view rendering for the field info list page.
 *
 * <p>Base path: {@code /fieldsInfo}</p>
 *
 * <p>Supports optional filtering by field name, grain type, area range,
 * humidity, and wind speed via query parameters.</p>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/fieldsInfo")
public class FieldInfoViewController {

    private final FieldInfoGetService getService;

    /**
     * Renders the fields-info page with an optionally filtered list of field infos.
     *
     * @param fieldName  optional filter for field name
     * @param grainType  optional filter for grain type
     * @param minArea    optional minimum surface area filter in square meters
     * @param maxArea    optional maximum surface area filter in square meters
     * @param humidity   optional minimum humidity filter as a percentage
     * @param wind       optional maximum wind speed filter in km/h
     * @param model      the Spring MVC {@link Model} used to pass attributes to the view
     * @return the logical name of the fields-info Thymeleaf template
     */
    @GetMapping
    public String getAllFields(
            @RequestParam(required = false) String fieldName,
            @RequestParam(required = false) Grain grainType,
            @RequestParam(required = false) Double minArea,
            @RequestParam(required = false) Double maxArea,
            @RequestParam(required = false) Double humidity,
            @RequestParam(required = false) Double wind,
            Model model
    ) {
        log.info("Received request to render fields-info view");
        FieldInfoListDto fields = getService.getFilteredFields(fieldName, grainType, minArea, maxArea, humidity, wind);
        model.addAttribute("fields", fields);
        model.addAttribute("activePage", "fields");
        model.addAttribute("grainType", grainType);
        model.addAttribute("grainTypes", Grain.values());
        log.debug("Added attributes to model: fields={}, grainType={}, grainTypes={}", fields, grainType, Grain.values());
        return "fields-info";
    }


}
