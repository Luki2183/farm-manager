package pl.luki2183.farmManager.dto;

import lombok.Data;

import java.util.List;

@Data
public class FieldDto {
    private String id;
    private List<PointDto> coordinates;
}
