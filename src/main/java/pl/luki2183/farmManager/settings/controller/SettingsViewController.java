package pl.luki2183.farmManager.settings.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.luki2183.farmManager.fieldInfo.model.Grain;
import pl.luki2183.farmManager.settings.dto.SettingsDto;
import pl.luki2183.farmManager.settings.service.SettingsGetService;
import pl.luki2183.farmManager.settings.service.SettingsPutService;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * MVC controller handling Thymeleaf view rendering for the settings page.
 *
 * <p>Base path: {@code /settings}</p>
 *
 * <p>Business logic delegated to {@link SettingsGetService} and {@link SettingsPutService}</p>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/settings")
public class SettingsViewController {

    private final SettingsGetService getService;
    private final SettingsPutService putService;

    /**
     * Renders the settings page with current application settings.
     *
     * @param model the Spring MVC {@link Model} used to pass attributes to the view
     * @return the logical name of the settings Thymeleaf template
     */
    @GetMapping
    public String getSettings(Model model) {
        log.info("Received request to render settings view");
        SettingsDto settings = getService.getSettings();
        model.addAttribute("activePage", "settings");
        model.addAttribute("settings", settings);
        log.debug("Added settings to model: {}", settings);
        return "settings";
    }

    /**
     * Handles bulk update of grain color mappings submitted from the settings form.
     *
     * <p>Filters out the {@code _method} parameter injected by form method spoofing,
     * then maps the remaining entries to a {@link Grain}-to-color map.</p>
     *
     * @param params             raw form parameters, where each key is a {@link Grain}
     *                           name and each value is a hex color string
     * @param redirectAttributes used to pass a success flash message after redirect
     * @return a redirect to {@code /settings}
     */
    @PutMapping("/colors")
    public String updateAllColors(
            @RequestParam Map<String, String> params,
            RedirectAttributes redirectAttributes
    ) {
        log.info("Received request to update grain colors with params: {}", params);
        Map<Grain, String> colors = params.entrySet().stream()
                .filter(e -> !e.getKey().equals("_method"))
                .collect(Collectors.toMap(
                        e -> Grain.valueOf(e.getKey()),
                        Map.Entry::getValue
                ));
        putService.updateColors(colors);
        log.info("Successfully updated {} grain color(s)", colors.size());
        redirectAttributes.addFlashAttribute("successMessage", "All colors saved.");
        return "redirect:/settings";
    }

}
