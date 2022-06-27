package com.yimaag.controller;

import com.yimaag.entity.Poster;
import com.yimaag.log.LogGenerator;
import com.yimaag.pojo.PosterP;
import com.yimaag.response.ErrorResponse;
import com.yimaag.service.PosterService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;

@RestController
@RequestMapping("/posters")
@EnableAutoConfiguration
@CrossOrigin
@Log4j2
@AllArgsConstructor
public class PosterController {
    private final PosterService posterService;

    @PostMapping
    public ResponseEntity create(@RequestBody PosterP posterP) {
        try {
            return ResponseEntity.ok(posterService.create(posterP));
        } catch (Exception e) {
            log.error(LogGenerator.error("Poster create can not execute"));
            log.error(LogGenerator.exception(e), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity allPosterList(@QueryParam("pageable") Boolean pageable,
                                        @QueryParam("page") Integer page,
                                        @QueryParam("itemCount") Integer itemCount,
                                        @QueryParam("posterDefinitionName") String posterDefinitionName,
                                        @QueryParam("contentTitle") String contentTitle,
                                        @QueryParam("sortParam") String[] sortParam) {
        pageable = pageable == null ? true : pageable;
        if (pageable) {
            return ResponseEntity.ok(posterService.getAllPageable(page, itemCount, posterDefinitionName, contentTitle, sortParam));
        }
        return ResponseEntity.ok(posterService.getAll(posterDefinitionName, contentTitle, sortParam));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        return ResponseEntity.ok(posterService.getById(id));
    }

    @PutMapping
    public ResponseEntity update(@RequestBody PosterP posterP) {
        Poster tempPoster = posterService.getById(posterP.getId());
        if (tempPoster == null) {
            return new ResponseEntity(
                    new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "There is no poster with id : " + posterP.getId()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(posterService.update(posterP));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        posterService.delete(id);
    }
}
