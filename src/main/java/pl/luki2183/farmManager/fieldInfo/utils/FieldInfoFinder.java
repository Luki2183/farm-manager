package pl.luki2183.farmManager.fieldInfo.utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.luki2183.farmManager.exception.model.FieldInfoEntityNotFoundException;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.repo.FieldInfoRepository;
import pl.luki2183.farmManager.utils.Finder;

import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class FieldInfoFinder implements Finder<FieldInfoEntity, String> {

    private final FieldInfoRepository repository;

    @Override
    public FieldInfoEntity find(String fieldId) {
        log.debug("Entering find with id: {}", fieldId);
        Optional<FieldInfoEntity> entity = repository.findByFieldId(fieldId);
        if (entity.isEmpty()) {
            log.warn("Did not find field info with id: {}", fieldId);
            throw new FieldInfoEntityNotFoundException();
        }
        FieldInfoEntity result = entity.get();
        log.debug("Found FieldInfoEntity result: {}", result);
        return result;
    }

    @Override
    public Boolean exists(String fieldId) {
        log.debug("Entering exists with id: {}", fieldId);
        boolean present = repository.existsByFieldId(fieldId);
        log.debug("Status of exists with id: {}, result: {}", fieldId, present);
        return present;
    }
}
