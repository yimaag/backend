package com.yimaag.repository;

import com.yimaag.entity.PlanProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PlanProfileRepository extends JpaRepository<PlanProfile, Long>, JpaSpecificationExecutor<PlanProfile> {
    PlanProfile findOneById(Long id);
}
