package com.yimaag.controller;

import com.yimaag.entity.FoodMaterials;
import com.yimaag.log.LogGenerator;
import com.yimaag.pojo.FoodMaterialsP;
import com.yimaag.response.ErrorResponse;
import com.yimaag.service.FoodMaterialsService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;


@RestController
@RequestMapping("/food_materials")
@EnableAutoConfiguration
@CrossOrigin
@Log4j2
@AllArgsConstructor
public class FoodMaterialsController {
    private final FoodMaterialsService foodMaterialsService;

    @PostMapping
    public ResponseEntity create(@RequestBody FoodMaterialsP foodMaterialsP) {
        try {
            return ResponseEntity.ok(foodMaterialsService.create(foodMaterialsP));
        } catch (Exception e) {
            log.error(LogGenerator.error("Food materials create can not execute"));
            log.error(LogGenerator.exception(e), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity allFoodMaterialsList(@QueryParam("pageable") Boolean pageable,
                                               @QueryParam("page") Integer page,
                                               @QueryParam("itemCount") Integer itemCount,
                                               @QueryParam("name") String name,
                                               @QueryParam("sortParam") String[] sortParam) {
        pageable = pageable == null ? true : pageable;
        if (pageable) {
            return ResponseEntity.ok(foodMaterialsService.getAllPageable(page, itemCount, name, sortParam));
        }
        return ResponseEntity.ok(foodMaterialsService.getAll(name, sortParam));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        return ResponseEntity.ok(foodMaterialsService.getById(id));
    }

    @PutMapping
    public ResponseEntity updateFoodMaterials(@RequestBody FoodMaterialsP foodMaterialsP) {
        FoodMaterials tempFoodMaterials = foodMaterialsService.getById(foodMaterialsP.getId());
        if (tempFoodMaterials == null) {
            return new ResponseEntity(
                    new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "There is no food materials with id : " + foodMaterialsP.getId()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(foodMaterialsService.update(foodMaterialsP));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        foodMaterialsService.delete(id);
    }
}
