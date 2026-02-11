package pl.luki2183.farmManager.fields.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.luki2183.farmManager.fields.model.FieldEntity;

import java.util.Optional;

public interface FieldRepository extends JpaRepository<FieldEntity, String>{
    Optional<FieldEntity> findByFieldId(String fieldId);

    boolean existsByFieldId(String id);
}
