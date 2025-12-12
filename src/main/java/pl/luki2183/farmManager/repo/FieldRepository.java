package pl.luki2183.farmManager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.luki2183.farmManager.entity.FieldEntity;

public interface FieldRepository extends JpaRepository<FieldEntity, String>{
}
