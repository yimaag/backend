package com.yimaag.service.implementation;

import com.yimaag.entity.PosterDefinition;
import com.yimaag.log.LogGenerator;
import com.yimaag.pojo.PosterDefinitionP;
import com.yimaag.repository.PosterDefinitionRepository;
import com.yimaag.service.PosterDefinitionService;
import com.yimaag.specification.FoodMaterialsSpecificationBuilder;
import com.yimaag.specification.PosterDefinitionSpecificationBuilder;
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
public class PosterDefinitionServiceImp implements PosterDefinitionService {
    private final PosterDefinitionRepository posterDefinitionRepository;
    @Override
    public PosterDefinition getById(Long id) {
        return posterDefinitionRepository.findOneById(id);
    }

    @Override
    public PosterDefinition create(PosterDefinitionP posterDefinitionP) {
        double width = posterDefinitionP.getWidth();
        double height = posterDefinitionP.getHeight();
        double aspectRatio = (width/height);
        PosterDefinition posterDefinition = new PosterDefinition();
        posterDefinition.setCode(posterDefinitionP.getCode());
        posterDefinition.setName(posterDefinitionP.getName());
        posterDefinition.setMandatoryPoster(posterDefinitionP.getMandatoryPoster());
        posterDefinition.setAspectRatio(String.format("%.3f",aspectRatio));
        posterDefinition.setWidth(posterDefinitionP.getWidth());
        posterDefinition.setHeight(posterDefinitionP.getHeight());
        posterDefinition.setFileSize(posterDefinitionP.getFileSize());
        posterDefinition.setCompressRatio(posterDefinitionP.getCompressRatio());
        posterDefinition = posterDefinitionRepository.save(posterDefinition);
        return posterDefinition;
    }

    @Override
    public Page<PosterDefinition> getAllPageable(Integer page, Integer itemCount, Integer code, String name, Boolean mandatoryPoster, String aspectRatio, Integer width, Integer height, Long fileSize, Integer compressRatio, String[] sortParam) {
        log.info(LogGenerator.enter("page : " + page + "| itemCount : " + itemCount + "| code : " + code +
                "| name : " + name + "| mandatoryPoster : " + mandatoryPoster + "| aspectRatio : " + aspectRatio +
                "| width : " + width + "| height : " + height + "| fileSize : " + fileSize + "| compressRatio : " + compressRatio +
                "| sortParam : " + sortParam));
        if (page == null) {
            page = 0;
        }
        if (itemCount == null) {
            itemCount = 10;
        }
        Pageable pageable;
        pageable = PageRequest.of(page, itemCount);
        PosterDefinitionSpecificationBuilder builder = new PosterDefinitionSpecificationBuilder();
        if (code != null) {
            builder.with("code", ":", code);
        }
        if (name != null) {
            builder.with("name", ":", name);
        }
        if (mandatoryPoster != null) {
            builder.with("mandatoryPoster", ":", mandatoryPoster);
        }
        if (aspectRatio != null) {
            builder.with("aspectRatio", ":", aspectRatio);
        }
        if (width != null) {
            builder.with("width", ":", width);
        }
        if (height != null) {
            builder.with("height", ":", height);
        }
        if (fileSize != null) {
            builder.with("fileSize", ":", fileSize);
        }
        if (compressRatio != null) {
            builder.with("compressRatio", ":", compressRatio);
        }
        List<String> sortParams;
        if (sortParam != null) {
            sortParams = Arrays.asList(sortParam);
            builder.with("sort", "sort", sortParams);
        } else {
            sortParams = Collections.singletonList("id-desc");
        }
        builder.with("sort", "sort", sortParams);

        return posterDefinitionRepository.findAll(builder.build(), pageable);
    }

    @Override
    public List<PosterDefinition> getAll(Integer code, String name, Boolean mandatoryPoster, String aspectRatio, Integer width, Integer height, Long fileSize, Integer compressRatio, String[] sortParam) {
        log.info(LogGenerator.enter("code : " + code +  "| name : " + name + "| mandatoryPoster : " + mandatoryPoster + "| aspectRatio : " + aspectRatio +
                "| width : " + width + "| height : " + height + "| fileSize : " + fileSize + "| compressRatio : " + compressRatio +
                "| sortParam : " + sortParam));
        PosterDefinitionSpecificationBuilder builder = new PosterDefinitionSpecificationBuilder();
        if (code != null) {
            builder.with("code", ":", code);
        }
        if (name != null) {
            builder.with("name", ":", name);
        }
        if (mandatoryPoster != null) {
            builder.with("mandatoryPoster", ":", mandatoryPoster);
        }
        if (aspectRatio != null) {
            builder.with("aspectRatio", ":", aspectRatio);
        }
        if (width != null) {
            builder.with("width", ":", width);
        }
        if (height != null) {
            builder.with("height", ":", height);
        }
        if (fileSize != null) {
            builder.with("fileSize", ":", fileSize);
        }
        if (compressRatio != null) {
            builder.with("compressRatio", ":", compressRatio);
        }
        List<String> sortParams;
        if (sortParam != null) {
            sortParams = Arrays.asList(sortParam);
            builder.with("sort", "sort", sortParams);
        } else {
            sortParams = Collections.singletonList("id-desc");
        }
        builder.with("sort", "sort", sortParams);

        return posterDefinitionRepository.findAll(builder.build());
    }

    @Override
    public PosterDefinition update(PosterDefinitionP posterDefinitionP) {
        double width = posterDefinitionP.getWidth();
        double height = posterDefinitionP.getHeight();
        double aspectRatio = (width/height);
        PosterDefinition posterDefinition = new PosterDefinition();
        posterDefinition.setId(posterDefinitionP.getId());
        posterDefinition.setCode(posterDefinitionP.getCode());
        posterDefinition.setName(posterDefinitionP.getName());
        posterDefinition.setMandatoryPoster(posterDefinitionP.getMandatoryPoster());
        posterDefinition.setAspectRatio(String.format("%.3f",aspectRatio));
        posterDefinition.setWidth(posterDefinitionP.getWidth());
        posterDefinition.setHeight(posterDefinitionP.getHeight());
        posterDefinition.setFileSize(posterDefinitionP.getFileSize());
        posterDefinition.setCompressRatio(posterDefinitionP.getCompressRatio());
        posterDefinition = posterDefinitionRepository.save(posterDefinition);
        return posterDefinition;
    }

    @Override
    public void delete(Long id) {
        posterDefinitionRepository.deleteById(id);
    }
}
