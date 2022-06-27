package com.yimaag.service;

import com.yimaag.constants.UserRole;
import com.yimaag.entity.User;
import com.yimaag.pojo.UserP;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    User getById(Long id);

    User create(UserP userP);

    Page<User> getAllPageable(Integer page, Integer itemCount, String name, String surname, String username,
                              String email, String password, UserRole[] userRole, Long creationTime, String[] sortParam);

    List<User> getAll(String name, String surname, String username,
                      String email, String password, UserRole[] userRole, Long creationTime, String[] sortParam);

    User update(UserP userP);

    void updatePassword(UserP userP);

    void delete(Long id);
}
