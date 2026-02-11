package pl.luki2183.farmManager.fields.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.exception.FieldEntityNotFoundException;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.fields.repo.FieldRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FieldDeleteService {

    private final FieldRepository repository;

    @Transactional
    public void deleteFieldById(String fieldId) {
        Optional<FieldEntity> fieldEntityOptional = repository.findByFieldId(fieldId);
        if (fieldEntityOptional.isEmpty()) throw new FieldEntityNotFoundException();
        repository.delete(fieldEntityOptional.get());
    }
}
