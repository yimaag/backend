package com.yimaag.controller;

import com.yimaag.entity.Content;
import com.yimaag.log.LogGenerator;
import com.yimaag.pojo.ContentP;
import com.yimaag.response.ErrorResponse;
import com.yimaag.service.ContentService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;

@RestController
@RequestMapping("/contents")
@EnableAutoConfiguration
@CrossOrigin
@Log4j2
@AllArgsConstructor
public class ContentController {
    private final ContentService contentService;
    @PostMapping
    public ResponseEntity create(@RequestBody ContentP contentP) {
        try {
            return ResponseEntity.ok(contentService.create(contentP));
        } catch (Exception e) {
            log.error(LogGenerator.error("Content create can not execute"));
            log.error(LogGenerator.exception(e), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity allContentList(@QueryParam("pageable") Boolean pageable,
                                          @QueryParam("page") Integer page,
                                          @QueryParam("itemCount") Integer itemCount,
                                          @QueryParam("title") String title,
                                          @QueryParam("description") String description,
                                          @QueryParam("sortParam") String[] sortParam) {
        pageable = pageable == null ? true : pageable;
        if (pageable) {
            return ResponseEntity.ok(contentService.getAllPageable(page, itemCount, title, description, sortParam));
        }
        return ResponseEntity.ok(contentService.getAll(title, description, sortParam));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        return ResponseEntity.ok(contentService.getById(id));
    }

    @PutMapping
    public ResponseEntity update(@RequestBody ContentP contentP) {
        Content tempContent = contentService.getById(contentP.getId());
        if (tempContent == null) {
            return new ResponseEntity(
                    new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "There is no content with id : " + contentP.getId()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(contentService.update(contentP));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        contentService.delete(id);
    }
}
