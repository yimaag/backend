package com.yimaag.controller;

import com.yimaag.constants.UserRole;
import com.yimaag.entity.User;
import com.yimaag.log.LogGenerator;
import com.yimaag.pojo.UserP;
import com.yimaag.response.ErrorResponse;
import com.yimaag.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;

@RestController
@RequestMapping("/users")
@EnableAutoConfiguration
@CrossOrigin
@Log4j2
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity create(@RequestBody UserP userP) {
        try {
            return ResponseEntity.ok(userService.create(userP));
        } catch (Exception e) {
            log.error(LogGenerator.error("User create can not execute"));
            log.error(LogGenerator.exception(e), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            e.getMessage()));
        }
    }

    @PostMapping("/admin")
    public ResponseEntity createAdmin(@RequestBody UserP userP) {
        try {
            return ResponseEntity.ok(userService.createAdmin(userP));
        } catch (Exception e) {
            log.error(LogGenerator.error("User create can not execute"));
            log.error(LogGenerator.exception(e), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity allUserList(@QueryParam("pageable") Boolean pageable,
                                      @QueryParam("page") Integer page,
                                      @QueryParam("itemCount") Integer itemCount,
                                      @QueryParam("name") String name,
                                      @QueryParam("surname") String surname,
                                      @QueryParam("username") String username,
                                      @QueryParam("email") String email,
                                      @QueryParam("password") String password,
                                      @QueryParam("userRole") UserRole[] userRole,
                                      @QueryParam("creationTime") Long creationTime,
                                      @QueryParam("sortParam") String[] sortParam) {
        pageable = pageable == null ? true : pageable;
        if (pageable) {
            return ResponseEntity.ok(userService.getAllPageable(page, itemCount, name, surname, username, email, password,
                    userRole, creationTime, sortParam));
        }
        return ResponseEntity.ok(userService.getAll(
                name, surname, username, email, password, userRole, creationTime, sortParam));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable Long id,
                                     @RequestBody UserP userP) {
        User tempUser = userService.getById(id);
        log.info(LogGenerator.enter("temp:" + tempUser.getId()));
        if (tempUser == null) {
            log.info(LogGenerator.enter());
            return new ResponseEntity(
                    new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "There is no User with id : " + id),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(userService.update(id,userP));
    }

    @PutMapping("/password/{id}")
    public ResponseEntity updatePassword(@PathVariable Long id,
                                         @RequestBody UserP userP) {
        userService.updatePassword(id, userP);
        log.info("Change password");
        return ResponseEntity.ok(id);
    }

    @PutMapping("admin/{id}")
    public ResponseEntity updateAdminUser(@PathVariable Long id,
                                          @RequestBody UserP userP) {
        User tempUser = userService.getById(id);
        log.info(LogGenerator.enter("temp:" + tempUser.getId()));
        if (tempUser == null) {
            log.info(LogGenerator.enter());
            return new ResponseEntity(
                    new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "There is no User with id : " + id),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(userService.updateAdmin(id,userP));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
