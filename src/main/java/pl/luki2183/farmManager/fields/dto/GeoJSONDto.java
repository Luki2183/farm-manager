package pl.luki2183.farmManager.fields.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.luki2183.farmManager.fieldInfo.model.Grain;

import java.util.List;

/**
 * Data Transfer Object representing a GeoJSON {@code Feature} for a farm field.
 *
 * <p>Conforms to the GeoJSON specification (RFC 7946). Used both as the request
 * body when creating or updating fields, and as the response format for the
 * map frontend.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeoJSONDto {
    private String id;
    private String type = "Feature";
    private Double area;
    private Geometry geometry;
    private Properties properties = new Properties();
    private Grain grainType;

    @Data
    public static class Geometry {
        private String type = "Polygon";
        private List<List<double[]>> coordinates;
    }

    @Data
    public static class Properties {
        private String mode = "polygon";
    }
}
