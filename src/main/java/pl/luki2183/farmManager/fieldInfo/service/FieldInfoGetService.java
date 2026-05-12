package pl.luki2183.farmManager.fieldInfo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoDto;
import pl.luki2183.farmManager.fieldInfo.mapper.FieldInfoMapper;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.model.Grain;
import pl.luki2183.farmManager.fieldInfo.repo.FieldInfoRepository;
import pl.luki2183.farmManager.exception.model.FieldInfoEntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FieldInfoGetService {

    private final FieldInfoRepository repository;
    private final FieldInfoMapper mapper;

    public List<FieldInfoDto> getAllInfo() {
        return mapper.infoToDtoList(repository.findAll());
    }

    public FieldInfoDto getInfoByFieldId(String fieldId) {
        Optional<FieldInfoEntity> entity = repository.findByFieldId(fieldId);
        if (entity.isEmpty()) throw new FieldInfoEntityNotFoundException();
        return mapper.infoToDto(entity.get());
    }

    public List<FieldInfoDto> getFilteredFields(
            String name,
            Grain grainType,
            Double minArea,
            Double maxArea,
            Double humidity,
            Double wind
    ) {
        List<FieldInfoEntity> entities = repository.findAll().stream()
                .filter(entity -> grainType == null ||  entity.getGrainType().equals(grainType) || grainType.equals(Grain.DEFAULT))
                .filter(entity -> minArea == null || (entity.getSurfaceArea() >= minArea))
                .filter(entity -> maxArea == null || (entity.getSurfaceArea() <= maxArea))
                .filter(entity -> humidity == null || (entity.getSurfaceArea() >= humidity))
                .filter(entity -> wind == null || (entity.getSurfaceArea() <= wind))
                .filter(entity -> name == null || name.isBlank() || (entity.getFieldName().toLowerCase().contains(name.toLowerCase()))) // todo do naprawy idk
                .toList();
        return mapper.infoToDtoList(entities);
    }
}
