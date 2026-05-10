package pl.luki2183.farmManager.settings.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.luki2183.farmManager.exception.model.SettingsNotFoundException;
import pl.luki2183.farmManager.settings.model.SettingsEntity;

public interface SettingsRepository extends JpaRepository<SettingsEntity, Long> {
    default SettingsEntity loadSingletion() {
        return findById(1L).orElseThrow(SettingsNotFoundException::new);
    }
}
