package pl.luki2183.farmManager.fields.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.exception.FieldEntityNotFoundException;
import pl.luki2183.farmManager.fields.dto.GeoJSONDto;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.fields.repo.FieldRepository;
import pl.luki2183.farmManager.fields.utils.FieldUpdateHelper;

import java.util.Optional;

@Service
@AllArgsConstructor
public class FieldPutService {

    private final FieldRepository repository;
    private final FieldUpdateHelper helper;

    @Transactional
    public FieldEntity updateField(String fieldId, GeoJSONDto dto) {
        Optional<FieldEntity> existingEntity = repository.findByFieldId(fieldId);
        if (existingEntity.isEmpty()) throw new FieldEntityNotFoundException();
        return helper.update(existingEntity.get(), dto);
    }
}
