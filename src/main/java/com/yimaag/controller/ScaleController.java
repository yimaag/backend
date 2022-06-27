package com.yimaag.controller;

import com.yimaag.entity.Scale;
import com.yimaag.log.LogGenerator;
import com.yimaag.pojo.ScaleP;
import com.yimaag.response.ErrorResponse;
import com.yimaag.service.ScaleService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;

@RestController
@RequestMapping("/scales")
@EnableAutoConfiguration
@CrossOrigin
@Log4j2
@AllArgsConstructor
public class ScaleController {
    private final ScaleService scaleService;
    @PostMapping
    public ResponseEntity create(@RequestBody ScaleP scaleP) {
        try {
            return ResponseEntity.ok(scaleService.create(scaleP));
        } catch (Exception e) {
            log.error(LogGenerator.error("Scale create can not execute"));
            log.error(LogGenerator.exception(e), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity allScaleList(@QueryParam("pageable") Boolean pageable,
                                       @QueryParam("page") Integer page,
                                       @QueryParam("itemCount") Integer itemCount,
                                       @QueryParam("name") String name,
                                       @QueryParam("sortParam") String[] sortParam) {
        pageable = pageable == null ? true : pageable;
        if (pageable) {
            return ResponseEntity.ok(scaleService.getAllPageable(page, itemCount, name, sortParam));
        }
        return ResponseEntity.ok(scaleService.getAll(name, sortParam));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        return ResponseEntity.ok(scaleService.getById(id));
    }

    @PutMapping
    public ResponseEntity update(@RequestBody ScaleP scaleP) {
        Scale tempScale = scaleService.getById(scaleP.getId());
        if (tempScale == null) {
            return new ResponseEntity(
                    new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "There is no scale with id : " + scaleP.getId()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(scaleService.update(scaleP));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        scaleService.delete(id);
    }
}
