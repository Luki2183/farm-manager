package pl.luki2183.farmManager.fieldInfo.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoDto;
import pl.luki2183.farmManager.fieldInfo.mapper.FieldInfoMapper;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.repo.FieldInfoRepository;
import pl.luki2183.farmManager.fieldInfo.utils.FieldInfoUpdate;

@Service
@AllArgsConstructor
public class FieldInfoPutService {

    private final FieldInfoRepository repository;
    private final FieldInfoUpdate fieldInfoUpdate;

    @Transactional
    public FieldInfoEntity updateInfo(FieldInfoDto dto) {
        FieldInfoEntity existingEntity = repository.findByFieldId(dto.getFieldId());
        return fieldInfoUpdate.update(existingEntity, dto);
    }
}
