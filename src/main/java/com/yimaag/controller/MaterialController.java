package com.yimaag.controller;

import com.yimaag.entity.Material;
import com.yimaag.log.LogGenerator;
import com.yimaag.pojo.MaterialP;
import com.yimaag.response.ErrorResponse;
import com.yimaag.service.MaterialService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;

@RestController
@RequestMapping("/materials")
@EnableAutoConfiguration
@CrossOrigin
@Log4j2
@AllArgsConstructor
public class MaterialController {
    private final MaterialService materialService;

    @PostMapping
    public ResponseEntity create(@RequestBody MaterialP materialP) {
        try {
            log.info(LogGenerator.enter("material: " + materialP.getFoodMaterials() + " content: " + materialP.getContent()));
            return ResponseEntity.ok(materialService.create(materialP));
        } catch (Exception e) {
            log.error(LogGenerator.error("Material create can not execute"));
            log.error(LogGenerator.exception(e), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity allMaterialList(@QueryParam("pageable") Boolean pageable,
                                          @QueryParam("page") Integer page,
                                          @QueryParam("itemCount") Integer itemCount,
                                          @QueryParam("foodMaterialsName") String foodMaterialsName,
                                          @QueryParam("contentTitle") String contentTitle,
                                          @QueryParam("quantity") Long quantity,
                                          @QueryParam("scaleName") String scaleName,
                                          @QueryParam("sortParam") String[] sortParam) {
        pageable = pageable == null ? true : pageable;
        if (pageable) {
            return ResponseEntity.ok(materialService.getAllPageable(page, itemCount, foodMaterialsName, contentTitle, quantity, scaleName, sortParam));
        }
        return ResponseEntity.ok(materialService.getAll(foodMaterialsName, contentTitle, quantity, scaleName, sortParam));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        return ResponseEntity.ok(materialService.getById(id));
    }

    @PutMapping
    public ResponseEntity update(@RequestBody MaterialP materialP) {
        Material tempMaterial = materialService.getById(materialP.getId());
        if (tempMaterial == null) {
            return new ResponseEntity(
                    new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "There is no material with id : " + materialP.getId()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(materialService.update(materialP));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        materialService.delete(id);
    }
}
