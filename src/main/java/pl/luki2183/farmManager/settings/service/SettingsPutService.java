package pl.luki2183.farmManager.settings.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.fieldInfo.model.Grain;
import pl.luki2183.farmManager.fields.dto.PointDto;
import pl.luki2183.farmManager.fields.model.PointEntity;
import pl.luki2183.farmManager.settings.dto.SettingsDto;
import pl.luki2183.farmManager.settings.mapper.SettingsMapper;
import pl.luki2183.farmManager.settings.model.SettingsEntity;
import pl.luki2183.farmManager.settings.repo.SettingsRepository;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SettingsPutService {

    private final SettingsRepository repository;
    private final SettingsMapper mapper;

    public SettingsDto updateSettings(SettingsDto dto) {
        SettingsEntity entity = repository.loadSingletion();
        PointEntity centerEntity = new PointEntity();
        centerEntity.setLng(dto.getCenter().getLng());
        centerEntity.setLat(dto.getCenter().getLat());
        entity.setCenter(centerEntity);
        entity.getGrainColors().putAll(dto.getColorMap());
        return mapper.mapToDto(repository.save(entity));
    }

    public SettingsDto updateColors(Map<Grain, String> colors) {
        SettingsEntity entity = repository.loadSingletion();
        entity.setGrainColors(colors);
        return mapper.mapToDto(repository.save(entity));
    }

    public SettingsDto updateCenter(PointDto dto) {
        SettingsEntity entity = repository.loadSingletion();
        PointEntity centerEntity = new PointEntity();
        centerEntity.setLat(dto.getLat());
        centerEntity.setLng(dto.getLng());
        entity.setCenter(centerEntity);
        return mapper.mapToDto(repository.save(entity));
    }
}
