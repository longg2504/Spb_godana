package com.godana.domain.entity;

import com.godana.domain.dto.role.RoleDTO;
import com.godana.domain.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="roles")
@Accessors(chain = true)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    @OneToMany(targetEntity = User.class, fetch = FetchType.EAGER)
    private List<User> users;

    public RoleDTO toRoleDTO(){
        return new RoleDTO()
                .setId(id)
                .setCode(code)
                .setName(name);

    }
}
