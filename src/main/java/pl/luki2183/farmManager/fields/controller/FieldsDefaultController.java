package pl.luki2183.farmManager.fields.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.luki2183.farmManager.fieldInfo.model.Grain;

@Controller
public class FieldsDefaultController {

    @GetMapping("/")
    public String map(Model model) {
        model.addAttribute("activePage", "map");
        model.addAttribute("grainTypes", Grain.values());
        return "map";
    }

    @GetMapping("/{fieldId}")
    public String map(Model model, @PathVariable String fieldId) {
        model.addAttribute("activePage", "map");
        model.addAttribute("grainTypes", Grain.values());
        model.addAttribute("focusedFieldId", fieldId);
        return "map";
    }

    @GetMapping("/error")
    public String error(Model model) { return "error"; }
}
