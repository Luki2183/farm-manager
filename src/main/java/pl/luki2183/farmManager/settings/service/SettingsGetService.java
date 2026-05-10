package pl.luki2183.farmManager.settings.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.settings.dto.SettingsDto;
import pl.luki2183.farmManager.settings.mapper.SettingsMapper;
import pl.luki2183.farmManager.settings.repo.SettingsRepository;

@Service
@RequiredArgsConstructor
public class SettingsGetService {

    private final SettingsRepository repository;
    private final SettingsMapper mapper;

    public SettingsDto getSettings() {
        return mapper.mapToDto(repository.loadSingletion());
    }
}
