package com.yimaag.controller;

import com.yimaag.entity.PosterDefinition;
import com.yimaag.log.LogGenerator;
import com.yimaag.pojo.PosterDefinitionP;
import com.yimaag.response.ErrorResponse;
import com.yimaag.service.PosterDefinitionService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.text.DecimalFormat;

@RestController
@RequestMapping("/poster_definitions")
@EnableAutoConfiguration
@CrossOrigin
@Log4j2
@AllArgsConstructor
public class PosterDefinitionController {
    private final PosterDefinitionService posterDefinitionService;
    @PostMapping
    public ResponseEntity create(@RequestBody PosterDefinitionP posterDefinitionP) {
        try {
            return ResponseEntity.ok(posterDefinitionService.create(posterDefinitionP));
        } catch (Exception e) {
            log.error(LogGenerator.error("Poster Definition create can not execute"));
            log.error(LogGenerator.exception(e), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity allPosterDefinitionList(@QueryParam("pageable") Boolean pageable,
                                                  @QueryParam("page") Integer page,
                                                  @QueryParam("itemCount") Integer itemCount,
                                                  @QueryParam("code") Integer code,
                                                  @QueryParam("name") String name,
                                                  @QueryParam("mandatoryPoster") Boolean mandatoryPoster,
                                                  @QueryParam("aspectRatio") String aspectRatio,
                                                  @QueryParam("width") Integer width,
                                                  @QueryParam("height") Integer height,
                                                  @QueryParam("fileSize") Long fileSize,
                                                  @QueryParam("compressRatio") Integer compressRatio,
                                                  @QueryParam("sortParam") String[] sortParam) {
        pageable = pageable == null ? true : pageable;
        if (pageable) {
            return ResponseEntity.ok(posterDefinitionService.getAllPageable(page, itemCount, code, name, mandatoryPoster, aspectRatio, width, height, fileSize, compressRatio, sortParam));
        }
        return ResponseEntity.ok(posterDefinitionService.getAll(code, name, mandatoryPoster, aspectRatio, width, height, fileSize, compressRatio, sortParam));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        return ResponseEntity.ok(posterDefinitionService.getById(id));
    }

    @PutMapping
    public ResponseEntity update(@RequestBody PosterDefinitionP posterDefinitionP) {
        PosterDefinition tempPosterDefinition = posterDefinitionService.getById(posterDefinitionP.getId());
        if (tempPosterDefinition == null) {
            return new ResponseEntity(
                    new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "There is no posterDefinition with id : " + posterDefinitionP.getId()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(posterDefinitionService.update(posterDefinitionP));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        posterDefinitionService.delete(id);
    }
}
