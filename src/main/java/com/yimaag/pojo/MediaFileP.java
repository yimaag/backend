package com.yimaag.pojo;

import com.yimaag.constants.MediaType;
import com.yimaag.entity.Content;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id"})
public class MediaFileP {
    private Long id;
    private String fileName;
    private Long fileSize;
    private MediaType mediaType;
    private Content content;
    private Boolean profiled;
    private Boolean transferred;
}
