package pl.luki2183.farmManager.fieldInfo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.util.Date;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldInfoEntity {
    @Id
    private String id;

    private String fieldId;

    private Double surfaceArea;

    private Grain grainType;

    private Date plantDate;

    private Date expectedHarvestDate;

    private Double humidity;

    private Double windSpeed;

    private Color fieldColor;
}


