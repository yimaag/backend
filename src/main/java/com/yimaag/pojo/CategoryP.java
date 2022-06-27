package com.yimaag.pojo;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id"})
public class CategoryP {
    private Long id;
    private String name;
    private Boolean visibility;
}
