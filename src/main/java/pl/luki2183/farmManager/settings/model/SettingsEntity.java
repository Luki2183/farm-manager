package pl.luki2183.farmManager.settings.model;

import jakarta.persistence.*;
import lombok.*;
import pl.luki2183.farmManager.fieldInfo.model.Grain;
import pl.luki2183.farmManager.fields.model.PointEntity;

import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "settings")
public class SettingsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private PointEntity center;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "settings_grain_colors", joinColumns = @JoinColumn(name = "settings_id"))
    @MapKeyEnumerated
    @MapKeyColumn(name = "grain")
    private Map<Grain, String> grainColors;
}
