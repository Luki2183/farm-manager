package pl.luki2183.farmManager.settings.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.luki2183.farmManager.fieldInfo.model.Grain;
import pl.luki2183.farmManager.settings.dto.SettingsDto;
import pl.luki2183.farmManager.settings.service.SettingsGetService;
import pl.luki2183.farmManager.settings.service.SettingsPutService;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/settings")
public class SettingsViewController {

    private final SettingsGetService getService;
    private final SettingsPutService putService;

    @GetMapping
    public String getSettings(Model model) {
        SettingsDto settings = getService.getSettings();
        model.addAttribute("activePage", "settings");
        model.addAttribute("settings", settings);
        return "settings";
    }



    @PostMapping("/colors")
    public String updateAllColors(@RequestParam Map<String, String> params,
                                  RedirectAttributes redirectAttributes) {
        Map<Grain, String> colors = params.entrySet().stream()
                .filter(e -> !e.getKey().equals("_method"))
                .collect(Collectors.toMap(
                        e -> Grain.valueOf(e.getKey()),
                        Map.Entry::getValue
                ));
        putService.updateColors(colors);
        redirectAttributes.addFlashAttribute("successMessage", "All colors saved.");
        return "redirect:/settings";
    }

}
