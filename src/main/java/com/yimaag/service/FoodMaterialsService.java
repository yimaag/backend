package com.yimaag.service;

import com.yimaag.entity.FoodMaterials;
import com.yimaag.pojo.FoodMaterialsP;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FoodMaterialsService {
    FoodMaterials getById(Long id);

    FoodMaterials create(FoodMaterialsP foodMaterialsP);

    Page<FoodMaterials> getAllPageable(Integer page, Integer itemCount, String name, String[] sortParam);

    List<FoodMaterials> getAll(String name, String[] sortParam);

    FoodMaterials update(FoodMaterialsP foodMaterialsP);

    void delete(Long id);
}
