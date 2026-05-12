package pl.luki2183.farmManager.fieldInfo.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoDto;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoListDto;
import pl.luki2183.farmManager.fieldInfo.mapper.FieldInfoMapper;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.model.Grain;
import pl.luki2183.farmManager.fieldInfo.repo.FieldInfoRepository;
import pl.luki2183.farmManager.exception.model.FieldInfoEntityNotFoundException;
import pl.luki2183.farmManager.fieldInfo.utils.FieldInfoFinder;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class FieldInfoGetService {

    private final FieldInfoRepository repository;
    private final FieldInfoMapper mapper;
    private final FieldInfoFinder finder;

    public FieldInfoListDto getAllInfo() {
        log.info("Fetching all fields");
        FieldInfoListDto result = mapper.infoToDtoList(repository.findAll());
        log.debug("Retrieved all fields result: {}", result);
        return result;
    }

    public FieldInfoDto getInfoByFieldId(String fieldId) {
        log.info("Fetching field info with id: {}", fieldId);
        FieldInfoEntity entity = finder.find(fieldId);
        FieldInfoDto result = mapper.infoToDto(entity);
        log.debug("Retrieved field info: {}", result);
        return result;
    }

    public FieldInfoListDto getFilteredFields(
            String name,
            Grain grainType,
            Double minArea,
            Double maxArea,
            Double humidity,
            Double wind
    ) {
        log.info("Fetching all field info with filters: name={}, grainType={}, minArea={}, maxArea={}, humidity={}, wind={}", name, grainType, minArea, maxArea, humidity, wind);
        List<FieldInfoEntity> entities = repository.findAll().stream()
                .filter(entity -> grainType == null ||  entity.getGrainType().equals(grainType) || grainType.equals(Grain.DEFAULT))
                .filter(entity -> minArea == null || (entity.getSurfaceArea() >= minArea))
                .filter(entity -> maxArea == null || (entity.getSurfaceArea() <= maxArea))
                .filter(entity -> humidity == null || (entity.getSurfaceArea() >= humidity))
                .filter(entity -> wind == null || (entity.getSurfaceArea() <= wind))
                .filter(entity -> name == null || name.isBlank() || (entity.getFieldName().toLowerCase().contains(name.toLowerCase())))
                .toList();
        FieldInfoListDto result = mapper.infoToDtoList(entities);
        log.debug("Retrieved all fields result: {}", result);
        return result;
    }
}
