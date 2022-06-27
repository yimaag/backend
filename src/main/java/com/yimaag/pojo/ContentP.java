package com.yimaag.pojo;

import lombok.*;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id"})
public class ContentP {
    private Long id;
    private String title;
    private String description;
}
