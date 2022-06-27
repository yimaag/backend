package com.yimaag.pojo;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id"})
public class PosterDefinitionP {
    private Long id;
    private Integer code;
    private String name;
    private Boolean mandatoryPoster;
    private String aspectRatio;
    private Integer width;
    private Integer height;
    private Long fileSize;
    private Integer compressRatio;
}
