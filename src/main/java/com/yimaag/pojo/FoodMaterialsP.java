package com.yimaag.pojo;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id"})
public class FoodMaterialsP {
    private Long id;
    private String name;
}
