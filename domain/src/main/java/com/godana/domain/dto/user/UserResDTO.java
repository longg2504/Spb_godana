package com.godana.domain.dto.user;

import com.godana.domain.dto.role.RoleDTO;
import com.godana.domain.dto.userAvatar.UserAvatarDTO;
import com.godana.domain.entity.Role;
import com.godana.domain.entity.UserAvatar;
import com.godana.domain.enums.EUserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class UserResDTO {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private RoleDTO role;
    private String status;
    private UserAvatar avatar;

    public UserResDTO(Long id, String username, String email, String fullName, Role role, EUserStatus status, UserAvatar avatar) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.role = role.toRoleDTO();
        this.status = String.valueOf(status);
        this.avatar= avatar;
    }
}
