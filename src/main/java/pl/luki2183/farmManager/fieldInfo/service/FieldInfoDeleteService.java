package pl.luki2183.farmManager.fieldInfo.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.repo.FieldInfoRepository;
import pl.luki2183.farmManager.exception.FieldInfoNotFoundException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class FieldInfoDeleteService {

    private final FieldInfoRepository repository;

    @Transactional
    public void deleteById(String fieldId) {
        Optional<FieldInfoEntity> entity = repository.findByFieldId(fieldId);
        if (entity.isEmpty()) throw new FieldInfoNotFoundException();
        repository.delete(entity.get());
    }
}
