package com.yimaag.service.implementation;

import com.yimaag.entity.Category;
import com.yimaag.log.LogGenerator;
import com.yimaag.pojo.CategoryP;
import com.yimaag.repository.CategoryRepository;
import com.yimaag.service.CategoryService;
import com.yimaag.specification.CategorySpecificationBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Log4j2
@AllArgsConstructor
public class CategoryServiceImp implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category getById(Long id) {
        return categoryRepository.findOneById(id);
    }

    @Override
    public Category create(CategoryP categoryP) {
        Category category = new Category();
        log.info(LogGenerator.enter("category : " + categoryP));
        category.setName(categoryP.getName());
        category.setVisibility(true);
        category = categoryRepository.save(category);
        return category;
    }

    @Override
    public Page<Category> getAllPageable(Integer page, Integer itemCount, String name, Boolean visibility, String[] sortParam) {
        log.info(LogGenerator.enter("page : " + page + "| itemCount : " + itemCount + "| name : " + name +
                "| visibility : " + visibility + "| sortParam : " + sortParam));
        if (page == null) {
            page = 0;
        }
        if (itemCount == null) {
            itemCount = 10;
        }
        Pageable pageable;
        pageable = PageRequest.of(page, itemCount);
        CategorySpecificationBuilder builder = new CategorySpecificationBuilder();
        if (name != null) {
            builder.with("name", ":", name);
        }
        if (visibility != null) {
            builder.with("visibility", ":", visibility);
        }
        List<String> sortParams;
        if (sortParam != null) {
            sortParams = Arrays.asList(sortParam);
            builder.with("sort", "sort", sortParams);
        } else {
            sortParams = Collections.singletonList("id-desc");
        }
        builder.with("sort", "sort", sortParams);

        return categoryRepository.findAll(builder.build(), pageable);
    }

    @Override
    public List<Category> getAll(String name, Boolean visibility, String[] sortParam) {
        log.info(LogGenerator.enter("name : " + name + "| visibility : " + visibility + "| sortParam : " + sortParam));
        CategorySpecificationBuilder builder = new CategorySpecificationBuilder();
        if (name != null) {
            builder.with("name", ":", name);
        }
        if (visibility != null) {
            builder.with("visibility", ":", visibility);
        }
        List<String> sortParams;
        if (sortParam != null) {
            sortParams = Arrays.asList(sortParam);
            builder.with("sort", "sort", sortParams);
        } else {
            sortParams = Collections.singletonList("id-desc");
        }
        builder.with("sort", "sort", sortParams);

        return categoryRepository.findAll(builder.build());
    }

    @Override
    public Category update(CategoryP categoryP) {
        Category categoryDB = categoryRepository.findOneById(categoryP.getId());
        Category category = new Category();
        log.info(LogGenerator.enter("category : " + categoryP));
        category.setId(categoryP.getId());
        category.setName(categoryP.getName());
        category.setVisibility(categoryDB.getVisibility());
        categoryRepository.save(category);
        return category;
    }

    @Override
    public Category updateVisibility(CategoryP categoryP) {
        Category categoryDB = categoryRepository.findOneById(categoryP.getId());
        Category category = new Category();
        log.info(LogGenerator.enter("category : " + categoryP));
        category.setId(categoryP.getId());
        category.setVisibility(categoryP.getVisibility());
        category.setName(categoryDB.getName());
        categoryRepository.save(category);
        return category;
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
