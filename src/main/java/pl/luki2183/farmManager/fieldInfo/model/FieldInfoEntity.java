package pl.luki2183.farmManager.fieldInfo.model;

import jakarta.persistence.*;
import lombok.*;
import pl.luki2183.farmManager.fields.model.FieldEntity;

import java.awt.*;
import java.util.Date;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "field_info_entities")
public class FieldInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "field_id", referencedColumnName = "id")
    private FieldEntity field;

    private Double surfaceArea;

    private Grain grainType;

    private Date plantDate;

    private Date expectedHarvestDate;

    private WeatherInfoEntity weatherInfo;

    private Color fieldColor;
}


