package pl.luki2183.farmManager.fieldInfo.dto;

import lombok.Data;

/**
 * Data Transfer Object representing a complete field info record,
 * including agronomic data and current weather conditions.
 */
@Data
public class FieldInfoDto {
    /** Business identifier of the associated field. */
    private String fieldId;
    /** Surface area of the field in square meters, rounded to two decimal places. */
    private double surfaceArea;
    /** Grain type currently planted on the field. */
    private String grainType;
    /** Planting date formatted as {@code yyyy-MM-dd}. */
    private String plantDate;
    /** Expected harvest date formatted as {@code yyyy-MM-dd}. */
    private String expectedHarvestDate;
    /** Current relative humidity at the field location, as a percentage (0–100). */
    private double humidity;
    /** Current wind speed at the field location in km/h. */
    private double windSpeed;
    /** Human-readable name of the field. */
    private String fieldName;
}
