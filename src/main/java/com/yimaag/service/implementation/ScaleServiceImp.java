package com.yimaag.service.implementation;

import com.yimaag.entity.Scale;
import com.yimaag.log.LogGenerator;
import com.yimaag.pojo.ScaleP;
import com.yimaag.repository.ScaleRepository;
import com.yimaag.service.ScaleService;
import com.yimaag.specification.FoodMaterialsSpecificationBuilder;
import com.yimaag.specification.ScaleSpecificationBuilder;
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
public class ScaleServiceImp implements ScaleService {
    private final ScaleRepository scaleRepository;
    @Override
    public Scale getById(Long id) {
        return scaleRepository.findOneById(id);
    }

    @Override
    public Scale create(ScaleP scaleP) {
        Scale scale = new Scale();
        log.info(LogGenerator.enter("Scale : " + scaleP));
        scale.setName(scaleP.getName());
        scale = scaleRepository.save(scale);
        return scale;
    }

    @Override
    public Page<Scale> getAllPageable(Integer page, Integer itemCount, String name, String[] sortParam) {
        log.info(LogGenerator.enter("page : " + page + "| itemCount : " + itemCount + "| name : " + name + "| sortParam : " + sortParam));
        if (page == null) {
            page = 0;
        }
        if (itemCount == null) {
            itemCount = 10;
        }
        Pageable pageable;
        pageable = PageRequest.of(page, itemCount);
        ScaleSpecificationBuilder builder = new ScaleSpecificationBuilder();
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

        return scaleRepository.findAll(builder.build(), pageable);
    }

    @Override
    public List<Scale> getAll(String name, String[] sortParam) {
        log.info(LogGenerator.enter("name : " + name + "| sortParam : " + sortParam));
        ScaleSpecificationBuilder builder = new ScaleSpecificationBuilder();
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

        return scaleRepository.findAll(builder.build());
    }

    @Override
    public Scale update(ScaleP scaleP) {
        Scale scale = new Scale();
        log.info(LogGenerator.enter("Scale : " + scaleP));
        scale.setId(scaleP.getId());
        scale.setName(scaleP.getName());
        scale = scaleRepository.save(scale);
        return scale;
    }

    @Override
    public void delete(Long id) {
        scaleRepository.deleteById(id);
    }
}
