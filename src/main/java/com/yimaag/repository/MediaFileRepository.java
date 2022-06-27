package com.yimaag.repository;

import com.yimaag.entity.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MediaFileRepository extends JpaRepository<MediaFile, Long>, JpaSpecificationExecutor<MediaFile> {
    MediaFile findOneById(Long id);
}
