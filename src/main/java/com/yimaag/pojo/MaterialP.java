package com.yimaag.pojo;

import com.yimaag.entity.Content;
import com.yimaag.entity.FoodMaterials;
import com.yimaag.entity.Scale;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id"})
public class MaterialP {
    private Long id;
    private FoodMaterials foodMaterials;
    private Content content;
    private Long quantity;
    private Scale scale;
}
