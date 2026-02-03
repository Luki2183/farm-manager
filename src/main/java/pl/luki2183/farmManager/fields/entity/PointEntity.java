package pl.luki2183.farmManager.fields.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class PointEntity {
    private double lat;
    private double lng;
}