package com.yimaag.controller;

import com.yimaag.constants.MediaType;
import com.yimaag.entity.MediaFile;
import com.yimaag.log.LogGenerator;
import com.yimaag.pojo.MediaFileP;
import com.yimaag.response.ErrorResponse;
import com.yimaag.service.MediaFileService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;

@RestController
@RequestMapping("/media_files")
@EnableAutoConfiguration
@CrossOrigin
@Log4j2
@AllArgsConstructor
public class MediaFileController {
    private final MediaFileService mediaFileService;

    @PostMapping
    public ResponseEntity create(@RequestBody MediaFileP mediaFileP) {
        try {
            return ResponseEntity.ok(mediaFileService.create(mediaFileP));
        } catch (Exception e) {
            log.error(LogGenerator.error("MediaFile create can not execute"));
            log.error(LogGenerator.exception(e), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity allMediaFileList(@QueryParam("pageable") Boolean pageable,
                                           @QueryParam("page") Integer page,
                                           @QueryParam("itemCount") Integer itemCount,
                                           @QueryParam("fileName") String fileName,
                                           @QueryParam("mediaType") MediaType[] mediaType,
                                           @QueryParam("contentTitle") String contentTitle,
                                           @QueryParam("profiled") Boolean profiled,
                                           @QueryParam("transferred") Boolean transferred,
                                           @QueryParam("sortParam") String[] sortParam) {
        pageable = pageable == null ? true : pageable;
        if (pageable) {
            return ResponseEntity.ok(mediaFileService.getAllPageable(page, itemCount, fileName, mediaType,
                    contentTitle, profiled, transferred, sortParam));
        }
        return ResponseEntity.ok(mediaFileService.getAll(fileName, mediaType, contentTitle, profiled,
                transferred, sortParam));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        return ResponseEntity.ok(mediaFileService.getById(id));
    }

    @PutMapping
    public ResponseEntity update(@RequestBody MediaFileP mediaFileP) {
        MediaFile tempMediaFile = mediaFileService.getById(mediaFileP.getId());
        if (tempMediaFile == null) {
            return new ResponseEntity(
                    new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "There is no mediaFile with id : " + mediaFileP.getId()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(mediaFileService.update(mediaFileP));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        mediaFileService.delete(id);
    }
}
