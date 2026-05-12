package pl.luki2183.farmManager.fieldInfo.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.exception.model.PrimaryKeyViolationException;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoCreateDto;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoDto;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoUpdateDto;
import pl.luki2183.farmManager.fieldInfo.mapper.FieldInfoMapper;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.utils.FieldInfoFinder;
import pl.luki2183.farmManager.fields.model.PointEntity;
import pl.luki2183.farmManager.fields.utils.FieldFinder;
import pl.luki2183.farmManager.weatherInfo.model.WeatherInfoEntity;
import pl.luki2183.farmManager.fieldInfo.repo.FieldInfoRepository;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.weatherInfo.service.WeatherGetService;
import pl.luki2183.farmManager.weatherInfo.utils.CoordinatesHelper;

@Slf4j
@Service
@AllArgsConstructor
public class FieldInfoPostService {

    private final FieldInfoRepository repository;
    private final WeatherGetService weatherGetService;
    private final FieldInfoMapper mapper;
    private final FieldFinder fieldFinder;
    private final FieldInfoFinder infoFinder;
    private final CoordinatesHelper coordinatesHelper;

    @Transactional
    public FieldInfoDto addInfo(FieldInfoCreateDto dto) {
        log.info("Creating FieldEntity: {}", dto);
        if (infoFinder.exists(dto.getFieldId())) {
            log.warn("Primary key violation error when creating FieldInfoEntity with id: {}", dto.getFieldId());
            throw new PrimaryKeyViolationException();
        }
        FieldEntity fieldEntity = fieldFinder.find(dto.getFieldId());
        PointEntity centerPoint = coordinatesHelper.getCenter(fieldEntity.getCoordinates());
        WeatherInfoEntity weatherInfo = weatherGetService.getWeatherInfoEntity(centerPoint);
        FieldInfoEntity entity = mapper.dtoToInfo(dto, fieldEntity, weatherInfo);
        fieldEntity.setFieldInfo(entity);
        FieldInfoDto result = mapper.infoToDto(repository.save(entity));
        log.debug("Created FieldEntity: {}", result);
        return result;
    }
}
