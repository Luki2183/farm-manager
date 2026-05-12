package pl.luki2183.farmManager.fieldInfo.dto;

import lombok.Data;

/**
 * Data Transfer Object carrying the fields that may be updated on an existing
 * field info record. All fields are optional — only non-null, non-blank values
 * are checked and applied by
 * {@link pl.luki2183.farmManager.fieldInfo.utils.FieldInfoUpdateHelper FieldInfoUpdateHelper}.
 *
 * <p>Date fields ({@code plantDate}, {@code expectedHarvestDate}) are expected
 * in {@code yyyy-MM-dd} format. {@code grainType} must match a valid
 * {@link pl.luki2183.farmManager.fieldInfo.model.Grain Grain} enum value.</p>
 */
@Data
public class FieldInfoUpdateDto {
    /** Business identifier of the associated field. */
    private String fieldId;
    /** Surface area of the field in square meters. */
    private Double surfaceArea;
    /** Grain type name; must match a {@link pl.luki2183.farmManager.fieldInfo.model.Grain Grain} enum value. */
    private String grainType;
    /** Planting date in {@code yyyy-MM-dd} format. */
    private String plantDate;
    /** Expected harvest date in {@code yyyy-MM-dd} format. */
    private String expectedHarvestDate;
    /** Human-readable name of the field. */
    private String fieldName;
}
