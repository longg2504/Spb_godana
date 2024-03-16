package com.godana.domain.dto.role;

import com.godana.domain.entity.Role;
import com.godana.domain.enums.ERole;
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
public class RoleDTO {
    private Long id;

    private String code;

    private ERole name;

    public Role toRole() {
        return new Role()
                .setId(id)
                .setCode(code)
                .setName(name)
                ;
    }
}
