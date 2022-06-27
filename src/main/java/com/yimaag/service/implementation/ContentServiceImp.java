package com.yimaag.service.implementation;

import com.yimaag.entity.Content;
import com.yimaag.log.LogGenerator;
import com.yimaag.pojo.ContentP;
import com.yimaag.repository.ContentRepository;
import com.yimaag.service.ContentService;
import com.yimaag.specification.ContentSpecificationBuilder;
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
public class ContentServiceImp implements ContentService {
    
    private final ContentRepository contentRepository;
    
    @Override
    public Content getById(Long id) {
        return contentRepository.findOneById(id);
    }

    @Override
    public Content create(ContentP contentP) {
        Content content = new Content();
        log.info(LogGenerator.enter("content : " + contentP));
        content.setTitle(contentP.getTitle());
        content.setDescription(contentP.getDescription());
        content = contentRepository.save(content);
        return content;
    }

    @Override
    public Page<Content> getAllPageable(Integer page, Integer itemCount, String title, String description, String[] sortParam) {
        log.info(LogGenerator.enter("page : " + page + "| itemCount : " + itemCount + "| title : " + title +
                "| description : " + description + "| sortParam : " + sortParam));
        if (page == null) {
            page = 0;
        }
        if (itemCount == null) {
            itemCount = 10;
        }
        Pageable pageable;
        pageable = PageRequest.of(page, itemCount);
        ContentSpecificationBuilder builder = new ContentSpecificationBuilder();
        if (title != null) {
            builder.with("title", ":", title);
        }
        if (description != null) {
            builder.with("description", ":", description);
        }
        List<String> sortParams;
        if (sortParam != null) {
            sortParams = Arrays.asList(sortParam);
            builder.with("sort", "sort", sortParams);
        } else {
            sortParams = Collections.singletonList("id-desc");
        }
        builder.with("sort", "sort", sortParams);

        return contentRepository.findAll(builder.build(), pageable);
    }

    @Override
    public List<Content> getAll(String title, String description, String[] sortParam) {
        log.info(LogGenerator.enter("title : " + title +
                "| description : " + description + "| sortParam : " + sortParam));
        ContentSpecificationBuilder builder = new ContentSpecificationBuilder();
        if (title != null) {
            builder.with("title", ":", title);
        }
        if (description != null) {
            builder.with("description", ":", description);
        }
        List<String> sortParams;
        if (sortParam != null) {
            sortParams = Arrays.asList(sortParam);
            builder.with("sort", "sort", sortParams);
        } else {
            sortParams = Collections.singletonList("id-desc");
        }
        builder.with("sort", "sort", sortParams);

        return contentRepository.findAll(builder.build());
    }

    @Override
    public Content update(ContentP contentP) {
        Content content = new Content();
        log.info(LogGenerator.enter("content : " + contentP));
        content.setId(contentP.getId());
        content.setTitle(contentP.getTitle());
        content.setDescription(contentP.getDescription());
        contentRepository.save(content);
        return content;
    }

    @Override
    public void delete(Long id) {
        contentRepository.deleteById(id);
    }
}
