package com.yimaag.service.implementation;

import com.yimaag.constants.UserRole;
import com.yimaag.entity.User;
import com.yimaag.log.LogGenerator;
import com.yimaag.pojo.UserP;
import com.yimaag.repository.UserRepository;
import com.yimaag.service.UserService;
import com.yimaag.specification.UserSpecificationBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Log4j2
@AllArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getById(Long id) {
        return userRepository.findOneById(id);
    }

    @Override
    public User create(UserP userP) {
        if (userP.getPassword().equals(userP.getPassword2())) {
            long now = System.currentTimeMillis();
            User user = new User();
            log.info(LogGenerator.enter("user : " + userP));
            user.setName(userP.getName());
            user.setSurname(userP.getSurname());
            user.setUsername(userP.getUsername());
            user.setEmail(userP.getEmail());
            user.setPassword(userP.getPassword());
            user.setUserRole(UserRole.USER);
            user.setCreationTime(now);
            user = userRepository.save(user);
            log.info(user.getUsername() + " user registered");
            return user;
        } else {
            log.error("Passwords do not match");
        }
        return null;
    }

    @Override
    public Page<User> getAllPageable(Integer page, Integer itemCount, String name, String surname,
                                     String username, String email, String password, UserRole[] userRole,
                                     Long creationTime, String[] sortParam) {
        log.info(LogGenerator.enter("page : " + page + "| itemCount : " + itemCount + "| name : " + name +
                "| surname : " + surname + "| username : " + username + "| email : " + email +
                "| password : " + password + "| userRole : " + userRole + "| creationTime : " + creationTime + "| sortParam : " + sortParam));
        if (page == null) {
            page = 0;
        }
        if (itemCount == null) {
            itemCount = 10;
        }
        Pageable pageable;
        pageable = PageRequest.of(page, itemCount);
        UserSpecificationBuilder builder = new UserSpecificationBuilder();
        if (name != null) {
            builder.with("name", ":", name);
        }
        if (surname != null) {
            builder.with("surname", ":", surname);
        }
        if (username != null) {
            builder.with("username", ":", username);
        }
        if (email != null) {
            builder.with("email", ":", email);
        }
        if (password != null) {
            builder.with("password", ":", password);
        }
        if (userRole != null) {
            List<UserRole> userRoles = Arrays.asList(userRole);
            builder.with("userRole", "in", userRoles);
        }
        if (creationTime != null) {
            builder.with("creationTime", ":", creationTime);
        }
        List<String> sortParams;
        if (sortParam != null) {
            sortParams = Arrays.asList(sortParam);
            builder.with("sort", "sort", sortParams);
        } else {
            sortParams = Collections.singletonList("id-desc");
        }
        builder.with("sort", "sort", sortParams);

        return userRepository.findAll(builder.build(), pageable);
    }

    @Override
    public List<User> getAll(String name, String surname, String username, String email, String password,
                             UserRole[] userRole, Long creationTime, String[] sortParam) {
        log.info(LogGenerator.enter("name : " + name + "| surname : " + surname + "| username : " + username + "| email : " + email +
                "| password : " + password + "| userRole : " + userRole + "| creationTime : " + creationTime + "| sortParam : " + sortParam));
        UserSpecificationBuilder builder = new UserSpecificationBuilder();
        if (name != null) {
            builder.with("name", ":", name);
        }
        if (surname != null) {
            builder.with("surname", ":", surname);
        }
        if (username != null) {
            builder.with("username", ":", username);
        }
        if (email != null) {
            builder.with("email", ":", email);
        }
        if (password != null) {
            builder.with("password", ":", password);
        }
        if (userRole != null) {
            builder.with("userRole", ":", userRole);
        }
        if (creationTime != null) {
            builder.with("creationTime", ":", creationTime);
        }
        List<String> sortParams;
        if (sortParam != null) {
            sortParams = Arrays.asList(sortParam);
            builder.with("sort", "sort", sortParams);
        } else {
            sortParams = Collections.singletonList("id-desc");
        }
        builder.with("sort", "sort", sortParams);

        return userRepository.findAll(builder.build());
    }

    @Override
    public User update(UserP userP) {
        log.info(LogGenerator.enter("user : " + userP));
        User userDB = userRepository.findOneById(userP.getId());
        User user = new User();
        user.setId(userP.getId());
        user.setName(userP.getName());
        user.setSurname(userP.getSurname());
        user.setUsername(userP.getUsername());
        user.setEmail(userP.getEmail());
        user.setPassword(userDB.getPassword());
        user.setUserRole(userP.getUserRole());
        user.setCreationTime(userDB.getCreationTime());
        userRepository.save(user);
        return user;
    }

    @Override
    public void updatePassword(UserP userP) {
        if (userP.getPassword().equals(userP.getPassword2())) {
            log.info(LogGenerator.enter("user : " + userP));
            User userDB = userRepository.findOneById(userP.getId());
            User user = new User();
            user.setId(userP.getId());
            user.setName(userDB.getName());
            user.setSurname(userDB.getSurname());
            user.setUsername(userDB.getUsername());
            user.setEmail(userDB.getEmail());
            user.setPassword(userP.getPassword());
            user.setUserRole(userDB.getUserRole());
            user.setCreationTime(userDB.getCreationTime());
            userRepository.save(user);
        } else {
            log.error("Passwords do not match");
        }
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
