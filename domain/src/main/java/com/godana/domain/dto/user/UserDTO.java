package com.godana.domain.dto.user;

import com.godana.domain.dto.role.RoleDTO;
import com.godana.domain.entity.User;
import com.godana.domain.entity.UserAvatar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private RoleDTO role;
    private UserAvatar avatar;

    public User toUser() {
        return new User()
                .setId(id)
                .setUsername(username)
                .setPassword(password)
                .setRole(role.toRole());

    }


}
