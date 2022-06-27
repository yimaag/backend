package com.yimaag.repository;

import com.yimaag.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContentRepository extends JpaRepository<Content, Long>, JpaSpecificationExecutor<Content> {
    Content findOneById(Long id);
}
