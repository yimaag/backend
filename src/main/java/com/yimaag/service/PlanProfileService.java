package com.yimaag.service;

import com.yimaag.entity.PlanProfile;
import com.yimaag.pojo.PlanProfileP;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PlanProfileService {
    PlanProfile getById(Long id);

    PlanProfile create(PlanProfileP planProfileP);

    Page<PlanProfile> getAllPageable(Integer page, Integer itemCount, String categoryName, Boolean categoryVisibility, String contentTitle, String[] sortParam);

    List<PlanProfile> getAll(String categoryName, Boolean categoryVisibility, String contentTitle, String[] sortParam);

    PlanProfile update(PlanProfileP planProfileP);

    void delete(Long id);
}
