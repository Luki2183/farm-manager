package pl.luki2183.farmManager.fields.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.exception.FieldEntityNotFoundException;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.fields.repo.FieldRepository;
import pl.luki2183.farmManager.utils.Finder;

import java.util.Optional;

@Component
@AllArgsConstructor
public class FieldFinder implements Finder<FieldEntity, String> {

    private final FieldRepository repository;

    @Override
    public FieldEntity find(String id) {
        Optional<FieldEntity> fieldEntityOptional = repository.findByFieldId(id);
        if (fieldEntityOptional.isEmpty()) throw new FieldEntityNotFoundException();
        return fieldEntityOptional.get();
    }
}
