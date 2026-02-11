package pl.luki2183.farmManager.fieldInfo.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.repo.FieldInfoRepository;
import pl.luki2183.farmManager.fieldInfo.utils.FieldInfoNotFoundException;

@Service
@AllArgsConstructor
public class FieldInfoDeleteService {

    private final FieldInfoRepository repository;

    @Transactional
    public void deleteById(String fieldId) {
        FieldInfoEntity entity = repository.findByFieldId(fieldId);
        if (entity == null) throw new FieldInfoNotFoundException("FieldInfoEntity not found with fieldId = ".concat(fieldId));
        repository.delete(entity);
    }
}
