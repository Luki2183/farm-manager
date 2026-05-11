package pl.luki2183.farmManager.settings.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.fieldInfo.model.Grain;
import pl.luki2183.farmManager.fields.dto.PointDto;
import pl.luki2183.farmManager.fields.model.PointEntity;
import pl.luki2183.farmManager.settings.dto.SettingsDto;
import pl.luki2183.farmManager.settings.mapper.SettingsMapper;
import pl.luki2183.farmManager.settings.model.SettingsEntity;
import pl.luki2183.farmManager.settings.repo.SettingsRepository;

import java.util.Map;

/**
 * Service class responsible for updating application settings.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SettingsPutService {

    private final SettingsRepository repository;
    private final SettingsMapper mapper;

    /**
     * Performs a full update of the settings, replacing both the center point
     * and the entire grain color map.
     *
     * @param dto the {@link SettingsDto} containing the new center and color map
     * @return the updated {@link SettingsDto} as persisted
     */
    public SettingsDto updateSettings(SettingsDto dto) {
        log.info("Updating all settings: {}", dto);
        SettingsEntity entity = repository.loadSingleton();
        PointEntity centerEntity = new PointEntity();
        centerEntity.setLng(dto.getCenter().getLng());
        centerEntity.setLat(dto.getCenter().getLat());
        entity.setCenter(centerEntity);
        entity.getGrainColors().putAll(dto.getGrainColors());
        SettingsDto result = mapper.fromEntityToDto(repository.save(entity));
        log.info("Successfully updated settings: {}", result);
        return result;
    }

    /**
     * Replaces the entire grain color map with the provided mapping.
     *
     * @param colors a map of {@link pl.luki2183.farmManager.fieldInfo.model.Grain}
     *               to hex color strings (e.g., {@code "#FF5733"})
     * @return the updated {@link SettingsDto} as persisted
     */
    public SettingsDto updateColors(Map<Grain, String> colors) {
        log.info("Updating grain colors, count: {}", colors.size());
        SettingsEntity entity = repository.loadSingleton();
        entity.setGrainColors(colors);
        SettingsDto result = mapper.fromEntityToDto(repository.save(entity));
        log.debug("Updated grain colors result: {}", result);
        return result;
    }

    /**
     * Updates only the map center point, leaving the grain color map unchanged.
     *
     * @param dto a {@link PointDto} containing the new latitude and longitude
     * @return the updated {@link SettingsDto} as persisted
     */
    public SettingsDto updateCenter(PointDto dto) {
        log.info("Updating center point: {}", dto);
        SettingsEntity entity = repository.loadSingleton();
        PointEntity centerEntity = new PointEntity();
        centerEntity.setLat(dto.getLat());
        centerEntity.setLng(dto.getLng());
        entity.setCenter(centerEntity);
        SettingsDto result = mapper.fromEntityToDto(repository.save(entity));
        log.debug("Successfully updated center point: {}", result);
        return result;
    }
}
