package com.godana.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangePasswordReqDTO implements Validator {
    @NotBlank(message = "Vui lòng nhập mật khẩu!")
    private String oldPassword;

    @Size(min = 6, max = 50, message = "Độ dài mật khẩu nằm trong khoảng 3-50 ký tự!")
    @NotBlank(message = "Vui lòng nhập mật khẩu mới!")
    private String newPassword;

    @Override
    public boolean supports(Class<?> clazz) {
        return ChangePasswordReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ChangePasswordReqDTO changePasswordReqDTO = (ChangePasswordReqDTO) target;
        String oldPassword = changePasswordReqDTO.oldPassword;
        String newPassword = changePasswordReqDTO.newPassword;
        if (oldPassword.isEmpty()) {
            errors.rejectValue("oldPassword", "oldPassword.null", "vui lòng nhập mật khẩu cũ");
            return;
        }

        if (newPassword.isEmpty()) {
            errors.rejectValue("newPassword", "newPassword.null", "vui lòng nhập mật khẩu mới");

        }
    }
}
