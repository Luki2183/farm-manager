package pl.luki2183.farmManager.fieldInfo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoDto;
import pl.luki2183.farmManager.fieldInfo.model.Grain;
import pl.luki2183.farmManager.fieldInfo.service.FieldInfoGetService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/fieldsInfo")
public class FieldInfoDefaultController {

    private final FieldInfoGetService getService;

    @GetMapping
    public String getAllFields(
            @RequestParam(required = false) Grain grainType,
            @RequestParam(required = false) Double minArea,
            @RequestParam(required = false) Double maxArea,
            @RequestParam(required = false) Double humidity,
            @RequestParam(required = false) Double wind,
            Model model
    ) {
        List<FieldInfoDto> fields = getService.getFilteredFields(grainType, minArea, maxArea, humidity, wind);
        model.addAttribute("fields", fields);
        model.addAttribute("activePage", "fields");
        model.addAttribute("grainType", grainType);
        model.addAttribute("grainTypes", Grain.values());
        return "fields-info";
    }
}
