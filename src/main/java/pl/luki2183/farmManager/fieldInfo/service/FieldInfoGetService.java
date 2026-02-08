package pl.luki2183.farmManager.fieldInfo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoDto;
import pl.luki2183.farmManager.fieldInfo.mapper.FieldInfoMapper;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.repo.FieldInfoRepository;
import pl.luki2183.farmManager.fieldInfo.utility.FieldInfoNotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class FieldInfoGetService {

    private final FieldInfoRepository repository;
    private final FieldInfoMapper mapper;

    public List<FieldInfoDto> getAllInfo() {
        return mapper.infoToDtoList(repository.findAll());
    }

    public FieldInfoDto getInfoByFieldId(String fieldId) {
        FieldInfoEntity entity = repository.findByFieldId(fieldId);
        if (entity == null) throw new FieldInfoNotFoundException("FieldInfoEntity not found with fieldId = ".concat(fieldId));
        return mapper.infoToDto(entity);
    }
}
