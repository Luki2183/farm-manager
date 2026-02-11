package pl.luki2183.farmManager.fields.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.fields.repo.FieldRepository;
import pl.luki2183.farmManager.utils.Finder;

@Component
@AllArgsConstructor
public class FieldFinder implements Finder<FieldEntity, String> {

    private final FieldRepository repository;

    @Override
    public FieldEntity find(String id) {
//        todo add notFoundExceptions
        return repository.getReferenceById(id);
    }
}
