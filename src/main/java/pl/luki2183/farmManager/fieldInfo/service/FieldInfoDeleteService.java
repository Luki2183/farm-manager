package pl.luki2183.farmManager.fieldInfo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.fieldInfo.mapper.FieldInfoMapper;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.repo.FieldInfoRepository;
import pl.luki2183.farmManager.fieldInfo.utility.FieldInfoNotFoundException;

@Service
@AllArgsConstructor
public class FieldInfoDeleteService {

    private final FieldInfoRepository repository;

    public void deleteById(String fieldId) {
        FieldInfoEntity entity = repository.findByFieldId(fieldId);
        if (entity == null) throw new FieldInfoNotFoundException("FieldInfoEntity not found with fieldId = ".concat(fieldId));
        repository.delete(entity);
    }
}
