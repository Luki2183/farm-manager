package pl.luki2183.farmManager.settings.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.luki2183.farmManager.exception.model.SettingsNotFoundException;
import pl.luki2183.farmManager.settings.model.SettingsEntity;

/**
 * Spring Data JPA repository for {@link SettingsEntity}.
 *
 * <p>Extends {@link JpaRepository} for standard CRUD operations and provides
 * a convenience method for loading the singleton settings record.</p>
 */
public interface SettingsRepository extends JpaRepository<SettingsEntity, Long> {
    /**
     * Loads the singleton settings record with {@code id = 1}.
     *
     * <p>The application is expected to maintain exactly one settings row at all times.
     * If the record is missing, a {@link SettingsNotFoundException} is thrown.</p>
     *
     * @return the single {@link SettingsEntity}
     * @throws SettingsNotFoundException if no settings record with {@code id = 1} exists
     */
    default SettingsEntity loadSingleton() {
        return findById(1L).orElseThrow(SettingsNotFoundException::new);
    }
}
