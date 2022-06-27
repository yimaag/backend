package com.yimaag.repository;

import com.yimaag.entity.PosterDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PosterDefinitionRepository extends JpaRepository<PosterDefinition, Long>, JpaSpecificationExecutor<PosterDefinition> {
    PosterDefinition findOneById(Long id);
}
