package com.godana.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserReqUpDTO implements Validator {

    @NotBlank(message = "Vui lòng nhập Họ tên!")
    public String fullname;

    @Pattern(regexp = "^[\\w]+@([\\w-]+\\.)+[\\w-]{2,6}$", message = "Email không hợp lệ!")
    @NotBlank(message = "Vui lòng nhập email!")
    public String email;

    public MultipartFile avatar;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserReqUpDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserReqUpDTO userReqUpDTO = (UserReqUpDTO) target;
        String fullName = userReqUpDTO.fullname;
        String email = userReqUpDTO.email;
        if (fullName.isEmpty()) {
            errors.rejectValue("fullName", "fullName.null", "Tên không được phép rỗng");
            return;
        }


        if (email.isEmpty()) {
            errors.rejectValue("email", "email.null", "Email không được phép rỗng");

        }
    }
    }

