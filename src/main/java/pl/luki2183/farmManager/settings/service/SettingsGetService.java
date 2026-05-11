package pl.luki2183.farmManager.settings.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.settings.dto.SettingsDto;
import pl.luki2183.farmManager.settings.mapper.SettingsMapper;
import pl.luki2183.farmManager.settings.repo.SettingsRepository;

/**
 * Service class responsible for retrieving application settings.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SettingsGetService {

    private final SettingsRepository repository;
    private final SettingsMapper mapper;

    /**
     * Loads the singleton settings record and returns it as a DTO.
     *
     * @return the current {@link SettingsDto}
     * @throws pl.luki2183.farmManager.exception.model.SettingsNotFoundException
     *         if the settings record does not exist
     */
    public SettingsDto getSettings() {
        log.info("Fetching application settings");
        SettingsDto result = mapper.fromEntityToDto(repository.loadSingleton());
        log.debug("Retrieved settings: {}", result);
        return result;
    }
}
