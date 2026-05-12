package pl.luki2183.farmManager.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.fieldInfo.model.Grain;
import pl.luki2183.farmManager.fields.model.PointEntity;
import pl.luki2183.farmManager.settings.model.SettingsEntity;
import pl.luki2183.farmManager.settings.repo.SettingsRepository;

import java.util.EnumMap;
import java.util.Map;

/**
 * Application startup component that seeds the database with default settings
 * if none exist yet.
 *
 * <p>Implements {@link ApplicationRunner} so it executes once automatically
 * after the Spring context is fully started. The initialization is idempotent —
 * if a settings record with {@code id = 1} already exists, the run is skipped.</p>
 *
 * <p>Default values applied on first run:</p>
 * <ul>
 *     <li>Center point: Warsaw, Poland (lat: {@code 52.2297}, lng: {@code 21.0122})</li>
 *     <li>Color for every {@link pl.luki2183.farmManager.fieldInfo.model.Grain} type: {@code #00AAFF}</li>
 * </ul>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {
    private final SettingsRepository settingsRepository;

    /**
     * Checks for an existing settings record and inserts defaults if none is found.
     *
     * @param args application arguments passed at startup (not used)
     * @throws Exception if an error occurs during initialization
     */
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

        SettingsEntity settings = SettingsEntity.builder()
                .center(defaultCenter)
                .grainColors(defaultColors)
                .build();
        settingsRepository.save(settings);
        log.info("Settings initialized with default center (lat: {}, lng: {}) and {} grain colors.",
                defaultCenter.getLat(), defaultCenter.getLng(), defaultColors.size());
    }
}
