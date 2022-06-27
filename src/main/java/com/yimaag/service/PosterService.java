package com.yimaag.service;

import com.yimaag.entity.Poster;
import com.yimaag.pojo.PosterP;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PosterService {
    Poster getById(Long id);

    Poster create(PosterP posterP);

    Page<Poster> getAllPageable(Integer page, Integer itemCount, String posterDefinitionName, String contentTitle, String[] sortParam);

    List<Poster> getAll(String posterDefinitionName, String contentTitle, String[] sortParam);

    Poster update(PosterP posterP);

    void delete(Long id);
}
