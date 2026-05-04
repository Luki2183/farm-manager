package pl.luki2183.farmManager.fieldInfo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoDto;
import pl.luki2183.farmManager.fieldInfo.service.FieldInfoGetService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/fieldsInfo")
public class FieldInfoDefaultController {

    private final FieldInfoGetService getService;

    @GetMapping
    public String getAllFields(Model model) {
        List<FieldInfoDto> fields = getService.getAllInfo();
        model.addAttribute("fields", fields);
        model.addAttribute("activePage", "fields");
        return "fields-info";
    }
}
