package com.yimaag.service.implementation;

import com.yimaag.entity.Content;
import com.yimaag.entity.FoodMaterials;
import com.yimaag.entity.Material;
import com.yimaag.entity.Scale;
import com.yimaag.log.LogGenerator;
import com.yimaag.pojo.MaterialP;
import com.yimaag.repository.MaterialRepository;
import com.yimaag.service.MaterialService;
import com.yimaag.specification.ContentSpecificationBuilder;
import com.yimaag.specification.MaterialSpecificationBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.mail.event.MailEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Service
@Log4j2
@AllArgsConstructor
public class MaterialServiceImp implements MaterialService {
    private final MaterialRepository materialRepository;

    @Override
    public Material getById(Long id) {
        return materialRepository.findOneById(id);
    }

    @Override
    public Material create(MaterialP materialP) {
        Material material = new Material();
        log.info(LogGenerator.enter("material : " + materialP));
        material.setFoodMaterials(materialP.getFoodMaterials());
        material.setContent(materialP.getContent());
        material.setQuantity(materialP.getQuantity());
        material.setScale(materialP.getScale());
        materialRepository.save(material);
        return material;
    }

    @Override
    public Page<Material> getAllPageable(Integer page, Integer itemCount, String foodMaterialsName, String contentTitle, Long quantity, String scaleName, String[] sortParam) {
        log.info(LogGenerator.enter("page : " + page + "| itemCount : " + itemCount + "| foodMaterialsName : " + foodMaterialsName +
                "| contentTitle : " + contentTitle + "| quantity : " + quantity + "| scaleName : " + scaleName + "| sortParam : " + sortParam));
        if (page == null) {
            page = 0;
        }
        if (itemCount == null) {
            itemCount = 10;
        }
        Pageable pageable;
        pageable = PageRequest.of(page, itemCount);
        MaterialSpecificationBuilder builder = new MaterialSpecificationBuilder();
        if (foodMaterialsName != null) {
            builder.with("foodMaterialsName", ":", foodMaterialsName);
        }
        if (contentTitle != null) {
            builder.with("contentTitle", ":", contentTitle);
        }
        if (quantity != null) {
            builder.with("quantity", ":", quantity);
        }
        if (scaleName != null) {
            builder.with("scaleName", ":", scaleName);
        }
        List<String> sortParams;
        if (sortParam != null) {
            sortParams = Arrays.asList(sortParam);
            builder.with("sort", "sort", sortParams);
        } else {
            sortParams = Collections.singletonList("id-desc");
        }
        builder.with("sort", "sort", sortParams);

        return materialRepository.findAll(builder.build(), pageable);
    }

    @Override
    public List<Material> getAll(String foodMaterialsName, String contentTitle, Long quantity, String scaleName, String[] sortParam) {
        log.info(LogGenerator.enter("foodMaterialsName : " + foodMaterialsName + "| contentTitle : " + contentTitle +
                "| quantity : " + quantity + "| scaleName : " + scaleName + "| sortParam : " + sortParam));

        MaterialSpecificationBuilder builder = new MaterialSpecificationBuilder();
        if (foodMaterialsName != null) {
            builder.with("foodMaterialsName", ":", foodMaterialsName);
        }
        if (contentTitle != null) {
            builder.with("contentTitle", ":", contentTitle);
        }
        if (quantity != null) {
            builder.with("quantity", ":", quantity);
        }
        if (scaleName != null) {
            builder.with("scaleName", ":", scaleName);
        }
        List<String> sortParams;
        if (sortParam != null) {
            sortParams = Arrays.asList(sortParam);
            builder.with("sort", "sort", sortParams);
        } else {
            sortParams = Collections.singletonList("id-desc");
        }
        builder.with("sort", "sort", sortParams);

        return materialRepository.findAll(builder.build());
    }

    @Override
    public Material update(MaterialP materialP) {
        Material material = new Material();
        log.info(LogGenerator.enter("material : " + materialP));
        material.setId(materialP.getId());
        material.setFoodMaterials(materialP.getFoodMaterials());
        material.setContent(materialP.getContent());
        material.setQuantity(materialP.getQuantity());
        material.setScale(materialP.getScale());
        materialRepository.save(material);
        return material;
    }

    @Override
    public void delete(Long id) {
        materialRepository.deleteById(id);
    }
}
