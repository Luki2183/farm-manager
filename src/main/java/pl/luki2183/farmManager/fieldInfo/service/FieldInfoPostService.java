package pl.luki2183.farmManager.fieldInfo.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoDto;
import pl.luki2183.farmManager.fieldInfo.mapper.FieldInfoMapper;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.model.WeatherInfoEntity;
import pl.luki2183.farmManager.fieldInfo.repo.FieldInfoRepository;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.fields.repo.FieldRepository;

@Service
@AllArgsConstructor
public class FieldInfoPostService {

    private final FieldInfoRepository repository;
    private final FieldRepository fieldRepository;
    private final WeatherGetService weatherGetService;
    private final FieldInfoMapper mapper;

    @Transactional
    public FieldInfoEntity addInfo(FieldInfoDto dto) {
        FieldEntity fieldEntity = fieldRepository.getReferenceById(dto.getFieldId());
        WeatherInfoEntity weatherInfo = weatherGetService.getWeatherInfo(fieldEntity.getCoordinates().getFirst());
        FieldInfoEntity entity = mapper.dtoToInfo(dto, fieldEntity, weatherInfo);
        return repository.save(entity);
    }
}
