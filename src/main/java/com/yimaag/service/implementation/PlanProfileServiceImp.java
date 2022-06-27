package com.yimaag.service.implementation;

import com.yimaag.entity.PlanProfile;
import com.yimaag.log.LogGenerator;
import com.yimaag.pojo.PlanProfileP;
import com.yimaag.repository.PlanProfileRepository;
import com.yimaag.service.PlanProfileService;
import com.yimaag.specification.MaterialSpecificationBuilder;
import com.yimaag.specification.PlanProfileSpecificationBuilder;
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
public class PlanProfileServiceImp implements PlanProfileService {
    private final PlanProfileRepository planProfileRepository;
    @Override
    public PlanProfile getById(Long id) {
        return planProfileRepository.findOneById(id);
    }

    @Override
    public PlanProfile create(PlanProfileP planProfileP) {
        PlanProfile planProfile = new PlanProfile();
        log.info(LogGenerator.enter("Plan profile : " +planProfileP));
        planProfile.setContent(planProfileP.getContent());
        planProfile.setCategory(planProfileP.getCategory());
        planProfile = planProfileRepository.save(planProfile);
        return planProfile;
    }

    @Override
    public Page<PlanProfile> getAllPageable(Integer page, Integer itemCount, String categoryName, Boolean categoryVisibility, String contentTitle, String[] sortParam) {
        log.info(LogGenerator.enter("page : " + page + "| itemCount : " + itemCount + "| categoryName : " + categoryName +
                "| categoryVisibility : " + categoryVisibility + "| contentTitle : " + contentTitle + "| sortParam : " + sortParam));
        if (page == null) {
            page = 0;
        }
        if (itemCount == null) {
            itemCount = 10;
        }
        Pageable pageable;
        pageable = PageRequest.of(page, itemCount);
        PlanProfileSpecificationBuilder builder = new PlanProfileSpecificationBuilder();
        if (categoryName != null) {
            builder.with("categoryName", ":", categoryName);
        }
        if (categoryVisibility != null) {
            builder.with("categoryVisibility", ":", categoryVisibility);
        }
        if (contentTitle != null) {
            builder.with("contentTitle", ":", contentTitle);
        }
        List<String> sortParams;
        if (sortParam != null) {
            sortParams = Arrays.asList(sortParam);
            builder.with("sort", "sort", sortParams);
        } else {
            sortParams = Collections.singletonList("id-desc");
        }
        builder.with("sort", "sort", sortParams);

        return planProfileRepository.findAll(builder.build(), pageable);
    }

    @Override
    public List<PlanProfile> getAll(String categoryName, Boolean categoryVisibility, String contentTitle, String[] sortParam) {
        log.info(LogGenerator.enter("categoryName : " + categoryName + "| categoryVisibility : " + categoryVisibility +
                "| contentTitle : " + contentTitle + "| sortParam : " + sortParam));
        PlanProfileSpecificationBuilder builder = new PlanProfileSpecificationBuilder();
        if (categoryName != null) {
            builder.with("categoryName", ":", categoryName);
        }
        if (categoryVisibility != null) {
            builder.with("categoryVisibility", ":", categoryVisibility);
        }
        if (contentTitle != null) {
            builder.with("contentTitle", ":", contentTitle);
        }
        List<String> sortParams;
        if (sortParam != null) {
            sortParams = Arrays.asList(sortParam);
            builder.with("sort", "sort", sortParams);
        } else {
            sortParams = Collections.singletonList("id-desc");
        }
        builder.with("sort", "sort", sortParams);

        return planProfileRepository.findAll(builder.build());
    }

    @Override
    public PlanProfile update(PlanProfileP planProfileP) {
        PlanProfile planProfile = new PlanProfile();
        log.info(LogGenerator.enter("Plan profile : " +planProfileP));
        planProfile.setId(planProfileP.getId());
        planProfile.setContent(planProfileP.getContent());
        planProfile.setCategory(planProfileP.getCategory());
        planProfile = planProfileRepository.save(planProfile);
        return planProfile;
    }

    @Override
    public void delete(Long id) {
        planProfileRepository.deleteById(id);
    }
}
