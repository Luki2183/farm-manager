package pl.luki2183.farmManager.fieldInfo.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoDto;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoUpdateDto;
import pl.luki2183.farmManager.fieldInfo.mapper.FieldInfoMapper;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.repo.FieldInfoRepository;
import pl.luki2183.farmManager.fieldInfo.utils.FieldInfoFinder;
import pl.luki2183.farmManager.fieldInfo.utils.FieldInfoUpdateHelper;
import pl.luki2183.farmManager.weatherInfo.model.WeatherInfoEntity;

/**
 * Service class responsible for updating existing field info records.
 *
 * <p>Uses {@link FieldInfoFinder} to resolve records before applying updates
 * via {@link FieldInfoUpdateHelper}.</p>
 */
@Slf4j
@Service
@AllArgsConstructor
public class FieldInfoPutService {

    private final FieldInfoRepository repository;
    private final FieldInfoUpdateHelper helper;
    private final FieldInfoMapper mapper;
    private final FieldInfoFinder finder;

    /**
     * Updates the agronomic data of the field info record identified by {@code fieldId}.
     *
     * @param dto     the {@link FieldInfoUpdateDto} containing the fields to update;
     *                only non-null, non-blank values are applied
     * @param fieldId the business identifier of the field info to update
     * @return the updated field info as a {@link FieldInfoDto}
     * @throws pl.luki2183.farmManager.exception.model.FieldInfoEntityNotFoundException
     *         if no field info with the given ID exists, thrown by
     *         {@link FieldInfoFinder#find(String)}
     */
    @Transactional
    public FieldInfoDto updateInfo(FieldInfoUpdateDto dto, String fieldId) {
        log.info("Updating FieldInfoEntity with id: {}, and data: {}", fieldId, dto);
        FieldInfoEntity existingEntity = finder.find(fieldId);
        FieldInfoEntity entity = helper.update(existingEntity, dto);
        FieldInfoDto result = mapper.infoToDto(repository.save(entity));
        log.debug("Updated FieldInfoEntity result: {}", result);
        return result;
    }

    /**
     * Updates only the weather data of the field info record identified by {@code fieldId}.
     *
     * <p>The update is applied only if both humidity and wind speed values
     * are non-null in the provided {@link WeatherInfoEntity}.</p>
     *
     * @param fieldId the business identifier of the field info to update
     * @param entity  the {@link WeatherInfoEntity} containing the new weather data
     * @return the updated field info as a {@link FieldInfoDto}
     * @throws pl.luki2183.farmManager.exception.model.FieldInfoEntityNotFoundException
     *         if no field info with the given ID exists, thrown by
     *         {@link FieldInfoFinder#find(String)}
     */
    @Transactional
    public FieldInfoDto updateWeather(String fieldId, WeatherInfoEntity entity) {
        log.info("Updating weather data of field info with id: {}, and data: {}", fieldId, entity);
        FieldInfoEntity existingEntity = finder.find(fieldId);
        FieldInfoEntity updatedEntity = helper.updateWeatherInfo(existingEntity, entity);
        FieldInfoDto result = mapper.infoToDto(repository.save(updatedEntity));
        log.debug("Updated weather data of field info, result: {}", result);
        return result;
    }
}
