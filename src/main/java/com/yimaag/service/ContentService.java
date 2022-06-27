package com.yimaag.service;

import com.yimaag.entity.Content;
import com.yimaag.pojo.ContentP;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContentService {
    Content getById(Long id);

    Content create(ContentP contentP);

    Page<Content> getAllPageable(Integer page, Integer itemCount, String title, String description, String[] sortParam);

    List<Content> getAll(String title, String description, String[] sortParam);

    Content update(ContentP contentP);

    void delete(Long id);
}
