package pl.luki2183.farmManager.fields.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;

import java.util.List;

/**
 * JPA entity representing a farm field, persisted in the {@code fields} table.
 *
 * <p>Each field has a business-facing {@code fieldId} (distinct from the
 * auto-generated database primary key) and an ordered list of geographic
 * boundary points stored in the {@code field_points} collection table.</p>
 *
 * <p>Cascades {@link CascadeType#REMOVE} to the associated
 * {@link FieldInfoEntity}, ensuring field info is deleted alongside the field.</p>
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fields")
public class FieldEntity {
    /** Auto-generated database primary key. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    /** Business identifier of the field, used in API paths and external references. */
    private String fieldId;

    /**
     * List of geographic coordinates defining the field boundary polygon,
     * eagerly loaded from the {@code field_points} collection table.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "field_points", joinColumns = @JoinColumn(name = "field_id"))
    private List<PointEntity> coordinates;

    /**
     * Associated field info record containing data such as grain type.
     * Excluded from JSON serialization to prevent circular references.
     */
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "field_info")
    @JsonBackReference
    private FieldInfoEntity fieldInfo;
}