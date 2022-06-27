package com.yimaag.service;

import com.yimaag.entity.PosterDefinition;
import com.yimaag.pojo.PosterDefinitionP;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PosterDefinitionService {
    PosterDefinition getById(Long id);

    PosterDefinition create(PosterDefinitionP posterDefinitionP);

    Page<PosterDefinition> getAllPageable(Integer page, Integer itemCount, Integer code, String name, Boolean mandatoryPoster, String aspectRatio, Integer width, Integer height, Long fileSize, Integer compressRatio, String[] sortParam);

    List<PosterDefinition> getAll(Integer code, String name, Boolean mandatoryPoster, String aspectRatio, Integer width, Integer height, Long fileSize, Integer compressRatio, String[] sortParam);

    PosterDefinition update(PosterDefinitionP posterDefinitionP);

    void delete(Long id);
}
