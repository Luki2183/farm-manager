package pl.luki2183.farmManager.fields.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Embeddable JPA entity representing a geographic coordinate point.
 *
 * <p>Embedded directly into parent entity tables or collection tables rather than mapped to its own table.</p>
 *
 * @see Embeddable
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PointEntity {
    /** Latitude in decimal degrees (range: -90 to 90). */
    private double lat;
    /** Longitude in decimal degrees (range: -180 to 180). */
    private double lng;
}