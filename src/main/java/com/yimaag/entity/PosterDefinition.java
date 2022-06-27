package com.yimaag.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = {"id"})
@ToString
public class PosterDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
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
