package com.yimaag.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yimaag.constants.MediaType;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = {"id"})
@ToString
public class MediaFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Long id;
    private String fileName;
    private Long fileSize;

    @ToString.Include
    @Enumerated(EnumType.STRING)
    private MediaType mediaType;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "content_id")
    private Content content;

    private Boolean profiled = false;
    private Boolean transferred = false;
}
