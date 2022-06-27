package com.yimaag.pojo;

import com.yimaag.entity.Content;
import com.yimaag.entity.PosterDefinition;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id"})
public class PosterP {
    private Long id;
    private String url;
    private Content content;
    private PosterDefinition posterDefinition;
}
