package com.yimaag.pojo;

import com.yimaag.constants.UserRole;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id"})
public class UserP {
    private Long id;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
    private String password2;
    private UserRole userRole;
    private Long creationTime;
}
