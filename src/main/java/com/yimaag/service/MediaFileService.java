package com.yimaag.service;

import com.yimaag.constants.MediaType;
import com.yimaag.entity.MediaFile;
import com.yimaag.pojo.MediaFileP;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MediaFileService {
    MediaFile getById(Long id);

    MediaFile create(MediaFileP mediaFileP);

    Page<MediaFile> getAllPageable(Integer page, Integer itemCount, String fileName, MediaType[] mediaType, String contentTitle,
                                   Boolean profiled, Boolean transferred, String[] sortParam);

    List<MediaFile> getAll(String fileName, MediaType[] mediaType, String contentTitle,
                           Boolean profiled, Boolean transferred, String[] sortParam);

    MediaFile update(MediaFileP mediaFileP);

    void delete(Long id);
}
