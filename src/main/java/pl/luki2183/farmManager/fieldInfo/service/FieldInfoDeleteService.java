package pl.luki2183.farmManager.fieldInfo.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;
import pl.luki2183.farmManager.fieldInfo.repo.FieldInfoRepository;
import pl.luki2183.farmManager.fieldInfo.utils.FieldInfoFinder;

@Slf4j
@Service
@AllArgsConstructor
public class FieldInfoDeleteService {

    private final FieldInfoRepository repository;
    private final FieldInfoFinder finder;

    @Transactional
    public void deleteById(String fieldId) {
        log.info("Deleting FieldInfoEntity with id: {}", fieldId);
        FieldInfoEntity entity = finder.find(fieldId);
        repository.delete(entity);
        log.debug("Successfully deleted FieldInfoEntity with id: {}", fieldId);
    }
}
