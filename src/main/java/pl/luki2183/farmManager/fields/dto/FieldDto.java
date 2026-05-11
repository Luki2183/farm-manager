package pl.luki2183.farmManager.fields.dto;

import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object representing a farm field.
 *
 * <p>Carries the field's business identifier and its list of
 * geographic boundary coordinates between layers.</p>
 */
@Data
public class FieldDto {
    /** Business identifier of the field. */
    private String id;
    /** List of geographic coordinates defining the field boundary polygon. */
    private List<PointDto> coordinates;
}
