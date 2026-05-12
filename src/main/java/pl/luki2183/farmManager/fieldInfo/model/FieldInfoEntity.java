package pl.luki2183.farmManager.fieldInfo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.weatherInfo.model.WeatherInfoEntity;

import java.time.LocalDate;

/**
 * JPA entity representing agronomic and weather information for a farm field,
 * persisted in the {@code field_infos} table.
 *
 * <p>Maintains a one-to-one relationship with {@link FieldEntity}, with
 * {@link CascadeType#REMOVE} ensuring this record is deleted if the associated
 * field is removed. Weather data is embedded directly via {@link WeatherInfoEntity}.</p>
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "field_infos")
public class FieldInfoEntity {
    /** Auto-generated database primary key. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    /** Business identifier of the associated field. */
    private String fieldId;

    /**
     * The associated {@link FieldEntity}. Cascade removal is enabled so that
     * deleting the field also removes this info record.
     * Excluded from JSON serialization to prevent circular references.
     */
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "field")
    @JsonManagedReference
    private FieldEntity field;

    /** Surface area of the field in square meters. */
    private Double surfaceArea;

    /** The type of grain currently planted on this field, stored as a string. */
    @Enumerated(EnumType.STRING)
    private Grain grainType;

    /** The date on which the crop was planted. */
    private LocalDate plantDate;

    /** The expected date of harvest. */
    private LocalDate expectedHarvestDate;

    /**
     * Current weather conditions at the field location, embedded directly
     * into the {@code field_infos} table columns.
     */
    private WeatherInfoEntity weatherInfo;

    /** Human-readable name of the field. */
    private String fieldName;
}


