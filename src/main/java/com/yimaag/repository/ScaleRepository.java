package com.yimaag.repository;

import com.yimaag.entity.Scale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ScaleRepository extends JpaRepository<Scale, Long>, JpaSpecificationExecutor<Scale> {
    Scale findOneById(Long id);
}
