package pl.luki2183.farmManager.settings.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.luki2183.farmManager.fields.dto.PointDto;
import pl.luki2183.farmManager.settings.dto.SettingsDto;
import pl.luki2183.farmManager.settings.service.SettingsGetService;
import pl.luki2183.farmManager.settings.service.SettingsPutService;

/**
 * REST controller exposing weather information endpoints.
 *
 * <p>Base path: {@code /api/settings}</p>
 *
 * <p>Business logic delegated to {@link SettingsGetService} and {@link SettingsPutService}</p>
 */
@Slf4j
@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
public class SettingsController {

    private final SettingsGetService getService;
    private final SettingsPutService putService;

    @GetMapping
    public ResponseEntity<SettingsDto> getSettings() {
        log.info("Received request to get settings");
        SettingsDto result = getService.getSettings();
        log.info("Successfully retrieved settings: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Updates the map center point.
     *
     * @param dto a {@link PointDto} representing the new center coordinates,
     *            provided in the request body
     * @return {@link ResponseEntity} containing the updated {@link SettingsDto}
     */
    @PutMapping
    public ResponseEntity<SettingsDto> updateCenter(
            @RequestBody PointDto dto
    ) {
        log.info("Received request to update center point: {}", dto);
        SettingsDto result = putService.updateCenter(dto);
        log.info("Successfully updated center point: {}", result);
        return ResponseEntity.ok(result);
    }
}
