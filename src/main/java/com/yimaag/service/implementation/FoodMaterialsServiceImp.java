package com.yimaag.service.implementation;

import com.yimaag.constants.UserRole;
import com.yimaag.entity.FoodMaterials;
import com.yimaag.log.LogGenerator;
import com.yimaag.pojo.FoodMaterialsP;
import com.yimaag.repository.FoodMaterialsRepository;
import com.yimaag.service.FoodMaterialsService;
import com.yimaag.specification.FoodMaterialsSpecificationBuilder;
import com.yimaag.specification.UserSpecificationBuilder;
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
public class FoodMaterialsServiceImp implements FoodMaterialsService {
    
    private final FoodMaterialsRepository foodMaterialsRepository;
    
    @Override
    public FoodMaterials getById(Long id) {
        return foodMaterialsRepository.findOneById(id);
    }

    @Override
    public FoodMaterials create(FoodMaterialsP foodMaterialsP) {
        FoodMaterials foodMaterials = new FoodMaterials();
        log.info(LogGenerator.enter("foodMaterials : " + foodMaterialsP));
        foodMaterials.setName(foodMaterialsP.getName());
        foodMaterials = foodMaterialsRepository.save(foodMaterials);
        return foodMaterials;
    }

    @Override
    public Page<FoodMaterials> getAllPageable(Integer page, Integer itemCount, String name, String[] sortParam) {
        log.info(LogGenerator.enter("page : " + page + "| itemCount : " + itemCount + "| name : " + name + "| sortParam : " + sortParam));
        if (page == null) {
            page = 0;
        }
        if (itemCount == null) {
            itemCount = 10;
        }
        Pageable pageable;
        pageable = PageRequest.of(page, itemCount);
        FoodMaterialsSpecificationBuilder builder = new FoodMaterialsSpecificationBuilder();
        if (name != null) {
            builder.with("name", ":", name);
        }
        List<String> sortParams;
        if (sortParam != null) {
            sortParams = Arrays.asList(sortParam);
            builder.with("sort", "sort", sortParams);
        } else {
            sortParams = Collections.singletonList("id-desc");
        }
        builder.with("sort", "sort", sortParams);

        return foodMaterialsRepository.findAll(builder.build(), pageable);
    }

    @Override
    public List<FoodMaterials> getAll(String name, String[] sortParam) {
        log.info(LogGenerator.enter("name : " + name + "| sortParam : " + sortParam));

        FoodMaterialsSpecificationBuilder builder = new FoodMaterialsSpecificationBuilder();
        if (name != null) {
            builder.with("name", ":", name);
        }
        List<String> sortParams;
        if (sortParam != null) {
            sortParams = Arrays.asList(sortParam);
            builder.with("sort", "sort", sortParams);
        } else {
            sortParams = Collections.singletonList("id-desc");
        }
        builder.with("sort", "sort", sortParams);

        return foodMaterialsRepository.findAll(builder.build());
    }

    @Override
    public FoodMaterials update(FoodMaterialsP foodMaterialsP) {
        FoodMaterials foodMaterials = new FoodMaterials();
        log.info(LogGenerator.enter("foodMaterials : " + foodMaterialsP));
        foodMaterials.setId(foodMaterialsP.getId());
        foodMaterials.setName(foodMaterialsP.getName());
        foodMaterialsRepository.save(foodMaterials);
        return foodMaterials;
    }

    @Override
    public void delete(Long id) {
        foodMaterialsRepository.deleteById(id);
    }
}
