package com.godana.domain.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCountDTO {
    private Long countUser;

    public UserCountDTO(Long countUser) {
        this.countUser = countUser;
    }
}
