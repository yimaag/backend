package com.yimaag.service;

import com.yimaag.entity.Category;
import com.yimaag.pojo.CategoryP;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    Category getById(Long id);

    Category create(CategoryP categoryP);

    Page<Category> getAllPageable(Integer page, Integer itemCount, String name, Boolean visibility, String[] sortParam);

    List<Category> getAll(String name, Boolean visibility, String[] sortParam);

    Category update(CategoryP categoryP);

    Category updateVisibility(CategoryP categoryP);

    void delete(Long id);
}
