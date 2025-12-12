package pl.luki2183.farmManager.dto;

import lombok.Data;

import java.util.List;

@Data
public class GeoJSONDto {
    private String id;
    private String type = "Feature";
    private Geometry geometry;
    private Properties properties = new Properties();

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
