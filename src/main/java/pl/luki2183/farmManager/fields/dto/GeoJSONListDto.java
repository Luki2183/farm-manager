package pl.luki2183.farmManager.fields.dto;

import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object representing a collection of GeoJSON features.
 *
 * <p>Wraps a list of {@link GeoJSONDto} objects alongside a total count,
 * intended as the top-level response payload for the map frontend.</p>
 */
@Data
public class GeoJSONListDto {
    /** The list of GeoJSON feature DTOs. */
    private List<GeoJSONDto> dtoList;
    /** Total number of GeoJSON features in the list. */
    private int count;
}
