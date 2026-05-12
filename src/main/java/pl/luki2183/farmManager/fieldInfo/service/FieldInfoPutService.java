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

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class FieldInfoPutService {

    private final FieldInfoRepository repository;
    private final FieldInfoUpdateHelper helper;
    private final FieldInfoMapper mapper;
    private final FieldInfoFinder finder;

    @Transactional
    public FieldInfoDto updateInfo(FieldInfoUpdateDto dto, String fieldId) {
        log.info("Updating FieldInfoEntity with id: {}, and data: {}", fieldId, dto);
        FieldInfoEntity existingEntity = finder.find(fieldId);
        FieldInfoEntity entity = helper.update(existingEntity, dto);
        FieldInfoDto result = mapper.infoToDto(entity);
        log.debug("Updated FieldInfoEntity result: {}", result);
        return result;
    }

    @Transactional
    public FieldInfoEntity updateWeather(String fieldId, WeatherInfoEntity entity) {
        log.info("Updating weather data of field info with id: {}, and data: {}", fieldId, entity);
        FieldInfoEntity existingEntity = finder.find(fieldId);
        FieldInfoEntity result = helper.updateWeatherInfo(existingEntity, entity);
        log.debug("Updated weather data of field info, result: {}", result);
        return result;
    }
}
