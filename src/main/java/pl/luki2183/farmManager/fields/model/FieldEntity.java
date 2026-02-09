package pl.luki2183.farmManager.fields.model;

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
    @Column(name = "id")
    private String id;

    @ElementCollection
    @CollectionTable(name = "field_points", joinColumns = @JoinColumn(name = "field_id"))
    private List<PointEntity> coordinates;

    private Double area;

    @OneToOne(mappedBy = "field_entities")
    private FieldInfoEntity fieldInfo;
}