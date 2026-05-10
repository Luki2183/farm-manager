package pl.luki2183.farmManager.settings.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.luki2183.farmManager.fields.dto.PointDto;
import pl.luki2183.farmManager.settings.dto.SettingsDto;
import pl.luki2183.farmManager.settings.service.SettingsGetService;
import pl.luki2183.farmManager.settings.service.SettingsPutService;

@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
public class SettingsController {

    private final SettingsGetService getService;
    private final SettingsPutService putService;

    @GetMapping
    public ResponseEntity<SettingsDto> getSettings() {
        return ResponseEntity.ok(getService.getSettings());
    }

    @PutMapping
    public ResponseEntity<SettingsDto> updateCenter(
            @RequestBody PointDto dto
    ) {
        return ResponseEntity.ok(putService.updateCenter(dto));
    }
}
