package pl.luki2183.farmManager.fieldInfo.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.exception.FieldInfoNotFoundException;
import pl.luki2183.farmManager.fieldInfo.dto.FieldInfoUpdateDto;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.repo.FieldInfoRepository;
import pl.luki2183.farmManager.fieldInfo.utils.FieldInfoUpdateHelper;

import java.util.Optional;

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
}
