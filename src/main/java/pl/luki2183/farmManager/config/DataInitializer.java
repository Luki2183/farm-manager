package pl.luki2183.farmManager.config;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.fieldInfo.model.Grain;
import pl.luki2183.farmManager.fields.model.PointEntity;
import pl.luki2183.farmManager.settings.model.SettingsEntity;
import pl.luki2183.farmManager.settings.repo.SettingsRepository;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {
    private final SettingsRepository settingsRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        if (settingsRepository.existsById(1L)) {
            log.info("Settings already initialized - skipping.");
            return;
        }

        PointEntity defaultCenter = new PointEntity();
        defaultCenter.setLng(21.0122);
        defaultCenter.setLat(52.2297);

        Map<Grain, String> defaultColors = new EnumMap<>(Grain.class);
        for (Grain type : Grain.values()) {
            defaultColors.put(type, "#00aaff");
        }

        SettingsEntity settings = new SettingsEntity();
        settings.setCenter(defaultCenter);
        settings.setGrainColors(defaultColors);
        settingsRepository.save(settings);
        log.info("Settings initialized with default center (lat: {}, lng: {}) and {} grain colors.",
                defaultCenter.getLat(), defaultCenter.getLng(), defaultColors.size());
    }
}
