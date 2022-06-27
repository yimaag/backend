package com.yimaag.repository;

import com.yimaag.entity.FoodMaterials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FoodMaterialsRepository extends JpaRepository<FoodMaterials, Long>, JpaSpecificationExecutor<FoodMaterials> {
    FoodMaterials findOneById(Long id);
}
