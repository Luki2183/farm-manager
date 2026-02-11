package pl.luki2183.farmManager.fields.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.luki2183.farmManager.fieldInfo.model.FieldInfoEntity;

import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "field_entities")
public class FieldEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String fieldId;

    @ElementCollection
    @CollectionTable(name = "field_points", joinColumns = @JoinColumn(name = "field_id"))
    private List<PointEntity> coordinates;

//    todo move area only to fieldInfo
    private Double area;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "field_info")
    @JsonBackReference
    private FieldInfoEntity fieldInfo;
}