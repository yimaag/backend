package com.yimaag.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = {"id"})
@ToString
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "food_materials_id")
    private FoodMaterials foodMaterials;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "content_id")
    private Content content;

    private Long quantity;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "scale_id")
    private Scale scale;

}
