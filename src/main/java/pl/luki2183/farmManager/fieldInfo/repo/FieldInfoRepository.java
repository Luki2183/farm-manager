package pl.luki2183.farmManager.fieldInfo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;

public interface FieldInfoRepository extends JpaRepository<FieldInfoEntity, String> {
    FieldInfoEntity findByFieldId(String fieldId);

    boolean existsByFieldId(String fieldId);
}
