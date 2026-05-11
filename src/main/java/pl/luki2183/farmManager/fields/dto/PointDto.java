package pl.luki2183.farmManager.fields.dto;

import lombok.Data;

/**
 * Data Transfer Object representing a geographic coordinate point.
 */
@Data
public class PointDto {
    /** Latitude in decimal degrees (range: -90 to 90). */
    private double lat;
    /** Longitude in decimal degrees (range: -180 to 180). */
    private double lng;
}