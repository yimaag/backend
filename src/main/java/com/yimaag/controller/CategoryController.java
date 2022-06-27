package com.yimaag.controller;

import com.yimaag.entity.Category;
import com.yimaag.log.LogGenerator;
import com.yimaag.pojo.CategoryP;
import com.yimaag.response.ErrorResponse;
import com.yimaag.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;

@RestController
@RequestMapping("/categories")
@EnableAutoConfiguration
@CrossOrigin
@Log4j2
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity create(@RequestBody CategoryP categoryP) {
        try {
            return ResponseEntity.ok(categoryService.create(categoryP));
        } catch (Exception e) {
            log.error(LogGenerator.error("Category create can not execute"));
            log.error(LogGenerator.exception(e), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity allCategoryList(@QueryParam("pageable") Boolean pageable,
                                          @QueryParam("page") Integer page,
                                          @QueryParam("itemCount") Integer itemCount,
                                          @QueryParam("name") String name,
                                          @QueryParam("visibility") Boolean visibility,
                                          @QueryParam("sortParam") String[] sortParam) {
        pageable = pageable == null ? true : pageable;
        if (pageable) {
            return ResponseEntity.ok(categoryService.getAllPageable(page, itemCount, name, visibility, sortParam));
        }
        return ResponseEntity.ok(categoryService.getAll(name, visibility, sortParam));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @PutMapping
    public ResponseEntity updateCategory(@RequestBody CategoryP categoryP) {
        Category tempCategory = categoryService.getById(categoryP.getId());
        if (tempCategory == null) {
            return new ResponseEntity(
                    new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "There is no category with id : " + categoryP.getId()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(categoryService.update(categoryP));
    }

    @PutMapping("/visibility")
    public ResponseEntity updateVisibility(@RequestBody CategoryP categoryP) {
        Category tempCategory = categoryService.getById(categoryP.getId());
        if (tempCategory == null) {
            return new ResponseEntity(
                    new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "There is no category with id : " + categoryP.getId()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(categoryService.updateVisibility(categoryP));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}
