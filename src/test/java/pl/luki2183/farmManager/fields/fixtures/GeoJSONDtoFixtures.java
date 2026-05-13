package pl.luki2183.farmManager.fields.fixtures;

import pl.luki2183.farmManager.fields.dto.GeoJSONDto;

import java.util.List;

public class GeoJSONDtoFixtures {
    public static GeoJSONDto.GeoJSONDtoBuilder valid() {
        GeoJSONDto.Geometry geometry = new GeoJSONDto.Geometry();
        geometry.setCoordinates(List.of(List.of(new double[]{21.0, 51.0})));
        return GeoJSONDto.builder()
                .id("field-1")
                .geometry(geometry);
    }

    public static GeoJSONDto.GeoJSONDtoBuilder withFieldId(String fieldId) {
        return valid().id(fieldId);
    }

    public static GeoJSONDto.GeoJSONDtoBuilder notValid() {
        return valid().id("").geometry(null);
    }
}
