package com.yimaag.repository;

import com.yimaag.entity.Poster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PosterRepository extends JpaRepository<Poster, Long>, JpaSpecificationExecutor<Poster> {
    Poster findOneById(Long id);
}
