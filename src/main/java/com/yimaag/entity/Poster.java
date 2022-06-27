package com.yimaag.entity;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = {"id"})
@ToString
public class Poster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Long id;
    private String url;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "content_id")
    private Content content;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "poster_definition_id")
    private PosterDefinition posterDefinition;
}
