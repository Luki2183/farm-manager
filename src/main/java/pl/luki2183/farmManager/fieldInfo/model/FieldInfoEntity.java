package pl.luki2183.farmManager.fieldInfo.model;

import jakarta.persistence.*;
import lombok.*;
import pl.luki2183.farmManager.fields.model.FieldEntity;

import java.awt.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "field_info_entities")
public class FieldInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // NEED TO DELETE THIS IN ORDER TO UPDATE BY POST METHOD
    private String id;

    private String fieldId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "field", referencedColumnName = "id")
    private FieldEntity field;

    private Double surfaceArea;

    @Enumerated(EnumType.STRING)
    private Grain grainType;

    private LocalDate plantDate;

    private LocalDate expectedHarvestDate;

    private WeatherInfoEntity weatherInfo;

    private Color fieldColor;
}


