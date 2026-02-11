package pl.luki2183.farmManager.fieldInfo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import pl.luki2183.farmManager.fields.model.FieldEntity;
import pl.luki2183.farmManager.weatherInfo.model.WeatherInfoEntity;

import java.awt.Color;
import java.time.LocalDate;

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

    private String fieldId;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "field")
    @JsonManagedReference
    private FieldEntity field;

    private Double surfaceArea;

    @Enumerated(EnumType.STRING)
    private Grain grainType;

    private LocalDate plantDate;

    private LocalDate expectedHarvestDate;

    private WeatherInfoEntity weatherInfo;

    private Color fieldColor;
}


