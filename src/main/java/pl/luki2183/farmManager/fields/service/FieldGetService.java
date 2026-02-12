package pl.luki2183.farmManager.fields.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.exception.FieldEntityNotFoundException;
import pl.luki2183.farmManager.fields.dto.FieldDto;
import pl.luki2183.farmManager.fields.mapper.FieldMapper;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.fields.repo.FieldRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FieldGetService {

    private final FieldRepository repository;
    private final FieldMapper mapper;

    public List<FieldDto> getAllFields(){
        return mapper.fieldsToDtoList(repository.findAll());
    }

    public FieldDto getFieldWithId(String fieldId) {
        Optional<FieldEntity> optionalField = repository.findByFieldId(fieldId);
        if (optionalField.isEmpty()) throw new FieldEntityNotFoundException();
        return mapper.fieldToDto(optionalField.get());
    }

    public Boolean checkIfExistsByFieldId(String fieldId) {
        return repository.existsByFieldId(fieldId);
    }
}
