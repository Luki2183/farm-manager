package pl.luki2183.farmManager.dto;

import lombok.Data;

import java.util.List;

@Data
public class FieldDto {
    private String name;
    private List<Point> coordinates;

    @Data
    public static class Point {
        private double lat;
        private double lng;
    }
}
