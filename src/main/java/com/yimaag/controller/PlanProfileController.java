package com.yimaag.controller;

import com.yimaag.entity.PlanProfile;
import com.yimaag.log.LogGenerator;
import com.yimaag.pojo.PlanProfileP;
import com.yimaag.response.ErrorResponse;
import com.yimaag.service.PlanProfileService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;

@RestController
@RequestMapping("/plan_profiles")
@EnableAutoConfiguration
@CrossOrigin
@Log4j2
@AllArgsConstructor
public class PlanProfileController {
    private final PlanProfileService planProfileService;

    @PostMapping
    public ResponseEntity create(@RequestBody PlanProfileP planProfileP) {
        try {
            return ResponseEntity.ok(planProfileService.create(planProfileP));
        } catch (Exception e) {
            log.error(LogGenerator.error("PlanProfile create can not execute"));
            log.error(LogGenerator.exception(e), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity allPlanProfileList(@QueryParam("pageable") Boolean pageable,
                                             @QueryParam("page") Integer page,
                                             @QueryParam("itemCount") Integer itemCount,
                                             @QueryParam("categoryName") String categoryName,
                                             @QueryParam("categoryVisibility") Boolean categoryVisibility,
                                             @QueryParam("contentTitle") String contentTitle,
                                             @QueryParam("sortParam") String[] sortParam) {
        pageable = pageable == null ? true : pageable;
        if (pageable) {
            return ResponseEntity.ok(planProfileService.getAllPageable(page, itemCount, categoryName, categoryVisibility,
                    contentTitle, sortParam));
        }
        return ResponseEntity.ok(planProfileService.getAll(categoryName,categoryVisibility,contentTitle, sortParam));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        return ResponseEntity.ok(planProfileService.getById(id));
    }

    @PutMapping
    public ResponseEntity update(@RequestBody PlanProfileP planProfileP) {
        PlanProfile tempPlanProfile = planProfileService.getById(planProfileP.getId());
        if (tempPlanProfile == null) {
            return new ResponseEntity(
                    new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "There is no planProfile with id : " + planProfileP.getId()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(planProfileService.update(planProfileP));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        planProfileService.delete(id);
    }
}
