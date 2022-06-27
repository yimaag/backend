package com.yimaag.service;


import com.yimaag.entity.Content;
import com.yimaag.entity.FoodMaterials;
import com.yimaag.entity.Material;
import com.yimaag.entity.Scale;
import com.yimaag.pojo.MaterialP;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MaterialService {
    Material getById(Long id);

    Material create(MaterialP materialP);

    Page<Material> getAllPageable(Integer page, Integer itemCount, String foodMaterialsName, String contentTitle, Long quantity, String scaleName, String[] sortParam);

    List<Material> getAll(String foodMaterialsName, String contentTitle, Long quantity, String scaleName, String[] sortParam);

    Material update(MaterialP materialP);

    void delete(Long id);
}
