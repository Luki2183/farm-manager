package pl.luki2183.farmManager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldEntity {
    @Id
    private String id;

    @ElementCollection
    @CollectionTable(name = "field_points", joinColumns = @JoinColumn(name = "field_id"))
    private List<PointEntity> coordinates;
}