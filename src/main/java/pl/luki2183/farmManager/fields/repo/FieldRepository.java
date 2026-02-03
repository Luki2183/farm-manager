package pl.luki2183.farmManager.fields.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.luki2183.farmManager.fields.entity.FieldEntity;

public interface FieldRepository extends JpaRepository<FieldEntity, String>{
}
