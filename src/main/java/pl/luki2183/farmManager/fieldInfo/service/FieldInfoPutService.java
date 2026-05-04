package pl.luki2183.farmManager.fieldInfo.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.exception.FieldInfoNotFoundException;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoUpdateDto;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.repo.FieldInfoRepository;
import pl.luki2183.farmManager.fieldInfo.utils.FieldInfoUpdateHelper;
import pl.luki2183.farmManager.weatherInfo.model.WeatherInfoEntity;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class FieldInfoPutService {

    private final FieldInfoRepository repository;
    private final FieldInfoUpdateHelper helper;

    @Transactional
    public FieldInfoEntity updateInfo(FieldInfoUpdateDto dto) {
        Optional<FieldInfoEntity> existingEntity = repository.findByFieldId(dto.getFieldId());
        if (existingEntity.isEmpty()) throw new FieldInfoNotFoundException();
        return helper.update(existingEntity.get(), dto);
    }

    @Transactional
    public FieldInfoEntity updateWeather(String fieldId, WeatherInfoEntity entity) {
        log.info("Entering updateWeather with: {}, {}", fieldId, entity);
        Optional<FieldInfoEntity> existingFieldInfo = repository.findByFieldId(fieldId);
        if (existingFieldInfo.isEmpty()) throw new FieldInfoNotFoundException();
        return helper.updateWeatherInfo(existingFieldInfo.get(), entity);
    }
}
