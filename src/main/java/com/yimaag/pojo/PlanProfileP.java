package com.yimaag.pojo;

import com.yimaag.entity.Category;
import com.yimaag.entity.Content;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id"})
public class PlanProfileP {
    private Long id;
    private Category category;
    private Content content;
}
