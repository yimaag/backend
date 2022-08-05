package com.yimaag.service;

import com.yimaag.constants.UserRole;
import com.yimaag.entity.User;
import com.yimaag.pojo.UserP;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    User getById(Long id);

    User create(UserP userP);

    User createAdmin(UserP userP);

    Page<User> getAllPageable(Integer page, Integer itemCount, String name, String surname, String username,
                              String email, String password, UserRole[] userRole, Long creationTime, String[] sortParam);

    List<User> getAll(String name, String surname, String username,
                      String email, String password, UserRole[] userRole, Long creationTime, String[] sortParam);

    User update(Long id, UserP userP);

    void updatePassword(Long id, UserP userP);

    User updateAdmin(Long id, UserP userP);

    void delete(Long id);
}
