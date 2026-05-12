package pl.luki2183.farmManager.settings.model;

import jakarta.persistence.*;
import lombok.*;
import pl.luki2183.farmManager.fieldInfo.model.Grain;
import pl.luki2183.farmManager.fields.model.PointEntity;

import java.util.Map;

/**
 * JPA entity representing the application settings, persisted in the {@code settings} table.
 *
 * <p>Designed as a singleton record — only one row with {@code id = 1} is expected
 * to exist at any time. Accessed via
 * {@link pl.luki2183.farmManager.settings.repo.SettingsRepository#loadSingleton() SettingsRepository.loadSingletion()} method.</p>
 *
 * <p>Grain color mappings are stored in a separate {@code settings_grain_colors}
 * collection table joined by {@code settings_id}.</p>
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "settings")
public class SettingsEntity {
    /** Auto-generated primary key. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Geographic center point embedded directly into the {@code settings} table columns.
     *
     * @see pl.luki2183.farmManager.fields.model.PointEntity
     */
    @Embedded
    private PointEntity center;

    /**
     * Map of {@link pl.luki2183.farmManager.fieldInfo.model.Grain} types to their
     * hex color strings (e.g., {@code "#FF5733"}), eagerly loaded from
     * the {@code settings_grain_colors} collection table.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "settings_grain_colors", joinColumns = @JoinColumn(name = "settings_id"))
    @MapKeyEnumerated
    @MapKeyColumn(name = "grain")
    private Map<Grain, String> grainColors;
}
