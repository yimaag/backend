package com.yimaag.service.implementation;

import com.yimaag.entity.Poster;
import com.yimaag.log.LogGenerator;
import com.yimaag.pojo.PosterP;
import com.yimaag.repository.PosterRepository;
import com.yimaag.service.PosterService;
import com.yimaag.specification.PosterSpecificationBuilder;
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
public class PosterServiceImp implements PosterService {
    private final PosterRepository posterRepository;
    @Override
    public Poster getById(Long id) {
        return posterRepository.findOneById(id);
    }

    @Override
    public Poster create(PosterP posterP) {
        Poster poster = new Poster();
        log.info(LogGenerator.enter("Poster : " + posterP));
        poster.setUrl(posterP.getUrl());
        poster.setContent(posterP.getContent());
        poster.setPosterDefinition(posterP.getPosterDefinition());
        poster = posterRepository.save(poster);
        return poster;
    }

    @Override
    public Page<Poster> getAllPageable(Integer page, Integer itemCount, String posterDefinitionName, String contentTitle, String[] sortParam) {
        log.info(LogGenerator.enter("page : " + page + "| itemCount : " + itemCount + "| posterDefinitionName : " + posterDefinitionName +
                "| contentTitle : " + contentTitle + "| sortParam : " + sortParam));
        if (page == null) {
            page = 0;
        }
        if (itemCount == null) {
            itemCount = 10;
        }
        Pageable pageable;
        pageable = PageRequest.of(page, itemCount);
        PosterSpecificationBuilder builder = new PosterSpecificationBuilder();
        if (posterDefinitionName != null) {
            builder.with("posterDefinitionName", ":", posterDefinitionName);
        }
        if (contentTitle != null) {
            builder.with("contentTitle", ":", contentTitle);
        }
        List<String> sortParams;
        if (sortParam != null) {
            sortParams = Arrays.asList(sortParam);
            builder.with("sort", "sort", sortParams);
        } else {
            sortParams = Collections.singletonList("id-desc");
        }
        builder.with("sort", "sort", sortParams);

        return posterRepository.findAll(builder.build(), pageable);
    }

    @Override
    public List<Poster> getAll(String posterDefinitionName, String contentTitle, String[] sortParam) {
        log.info(LogGenerator.enter("posterDefinitionName : " + posterDefinitionName + "| contentTitle : " + contentTitle +
                "| sortParam : " + sortParam));
        PosterSpecificationBuilder builder = new PosterSpecificationBuilder();
        if (posterDefinitionName != null) {
            builder.with("posterDefinitionName", ":", posterDefinitionName);
        }
        if (contentTitle != null) {
            builder.with("contentTitle", ":", contentTitle);
        }
        List<String> sortParams;
        if (sortParam != null) {
            sortParams = Arrays.asList(sortParam);
            builder.with("sort", "sort", sortParams);
        } else {
            sortParams = Collections.singletonList("id-desc");
        }
        builder.with("sort", "sort", sortParams);

        return posterRepository.findAll(builder.build());
    }

    @Override
    public Poster update(PosterP posterP) {
        Poster poster = new Poster();
        log.info(LogGenerator.enter("Poster : " + posterP));
        poster.setId(posterP.getId());
        poster.setUrl(posterP.getUrl());
        poster.setContent(posterP.getContent());
        poster.setPosterDefinition(posterP.getPosterDefinition());
        poster = posterRepository.save(poster);
        return poster;
    }

    @Override
    public void delete(Long id) {
        posterRepository.deleteById(id);
    }
}
