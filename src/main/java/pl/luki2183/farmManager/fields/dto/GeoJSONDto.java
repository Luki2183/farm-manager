package pl.luki2183.farmManager.fields.dto;

import lombok.Data;
import pl.luki2183.farmManager.fieldInfo.model.Grain;

import java.util.List;

@Data
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
