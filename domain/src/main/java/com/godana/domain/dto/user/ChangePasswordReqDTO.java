package com.godana.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NotNull
@Getter
@Setter
public class ChangePasswordReqDTO {
    private String oldPassword;
    private String newPassword;

}
