package com.yimaag.service.implementation;

import com.yimaag.constants.MediaType;
import com.yimaag.constants.UserRole;
import com.yimaag.entity.MediaFile;
import com.yimaag.log.LogGenerator;
import com.yimaag.pojo.MediaFileP;
import com.yimaag.repository.MediaFileRepository;
import com.yimaag.service.MediaFileService;
import com.yimaag.specification.MediaFileSpecificationBuilder;
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
public class MediaFileServiceImp implements MediaFileService {
    private final MediaFileRepository mediaFileRepository;

    @Override
    public MediaFile getById(Long id) {
        return mediaFileRepository.findOneById(id);
    }

    @Override
    public MediaFile create(MediaFileP mediaFileP) {
        MediaFile mediaFile = new MediaFile();
        mediaFile.setFileName(mediaFileP.getFileName());
        mediaFile.setFileSize(mediaFileP.getFileSize());
        mediaFile.setMediaType(mediaFileP.getMediaType());
        mediaFile.setContent(mediaFileP.getContent());
        mediaFile.setProfiled(mediaFileP.getProfiled());
        mediaFile.setTransferred(mediaFileP.getTransferred());
        mediaFile = mediaFileRepository.save(mediaFile);
        return mediaFile;
    }

    @Override
    public Page<MediaFile> getAllPageable(Integer page, Integer itemCount, String fileName, MediaType[] mediaType, String contentTitle, Boolean profiled, Boolean transferred, String[] sortParam) {
        log.info(LogGenerator.enter("page : " + page + "| itemCount : " + itemCount + "| fileName : " + fileName + "| mediaType : " + mediaType +
                "| contentTitle : " + contentTitle + "| profiled : " + profiled + "| transferred : " + transferred + "| sortParam : " + sortParam));
        if (page == null) {
            page = 0;
        }
        if (itemCount == null) {
            itemCount = 10;
        }
        Pageable pageable;
        pageable = PageRequest.of(page, itemCount);
        MediaFileSpecificationBuilder builder = new MediaFileSpecificationBuilder();
        if (fileName != null) {
            builder.with("fileName", ":", fileName);
        }
        if (mediaType != null) {
            List<MediaType> mediaTypes = Arrays.asList(mediaType);
            builder.with("mediaType", "in", mediaTypes);
        }
        if (contentTitle != null) {
            builder.with("contentTitle", ":", contentTitle);
        }
        if (profiled != null) {
            builder.with("profiled", ":", profiled);
        }
        if (transferred != null) {
            builder.with("transferred", ":", transferred);
        }
        List<String> sortParams;
        if (sortParam != null) {
            sortParams = Arrays.asList(sortParam);
            builder.with("sort", "sort", sortParams);
        } else {
            sortParams = Collections.singletonList("id-desc");
        }
        builder.with("sort", "sort", sortParams);

        return mediaFileRepository.findAll(builder.build(), pageable);
    }

    @Override
    public List<MediaFile> getAll(String fileName, MediaType[] mediaType, String contentTitle, Boolean profiled, Boolean transferred, String[] sortParam) {
        log.info(LogGenerator.enter("fileName : " + fileName + "| mediaType : " + mediaType +
                "| contentTitle : " + contentTitle + "| profiled : " + profiled + "| transferred : " + transferred + "| sortParam : " + sortParam));

        MediaFileSpecificationBuilder builder = new MediaFileSpecificationBuilder();
        if (fileName != null) {
            builder.with("fileName", ":", fileName);
        }
        if (mediaType != null) {
            List<MediaType> mediaTypes = Arrays.asList(mediaType);
            builder.with("mediaType", "in", mediaTypes);
        }
        if (contentTitle != null) {
            builder.with("contentTitle", ":", contentTitle);
        }
        if (profiled != null) {
            builder.with("profiled", ":", profiled);
        }
        if (transferred != null) {
            builder.with("transferred", ":", transferred);
        }
        List<String> sortParams;
        if (sortParam != null) {
            sortParams = Arrays.asList(sortParam);
            builder.with("sort", "sort", sortParams);
        } else {
            sortParams = Collections.singletonList("id-desc");
        }
        builder.with("sort", "sort", sortParams);

        return mediaFileRepository.findAll(builder.build());
    }

    @Override
    public MediaFile update(MediaFileP mediaFileP) {
        MediaFile mediaFile = new MediaFile();
        mediaFile.setId(mediaFileP.getId());
        mediaFile.setFileName(mediaFileP.getFileName());
        mediaFile.setFileSize(mediaFileP.getFileSize());
        mediaFile.setMediaType(mediaFileP.getMediaType());
        mediaFile.setContent(mediaFileP.getContent());
        mediaFile.setProfiled(mediaFileP.getProfiled());
        mediaFile.setTransferred(mediaFileP.getTransferred());
        mediaFile = mediaFileRepository.save(mediaFile);
        return mediaFile;
    }

    @Override
    public void delete(Long id) {
        mediaFileRepository.deleteById(id);
    }
}
