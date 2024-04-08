package com.godana.domain.dto.user;

import com.godana.domain.entity.Role;
import com.godana.domain.entity.User;
import com.godana.domain.entity.UserAvatar;
import com.godana.domain.enums.ERole;
import com.godana.domain.enums.EUserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class UserRegisterReqDTO implements Validator {
    private String username;
    private String password;
    private String fullName;
    private MultipartFile userAvatar;


    public User toUser(Role role, UserAvatar userAvatar, EUserStatus status) {
        return new User()
                .setUsername(username)
                .setPassword(password)
                .setEmail(username)
                .setRole(role)
                .setUserAvatar(userAvatar)
                .setStatus(status)
                .setFullName(fullName)
                ;
    }
    public User toUser(Role role, EUserStatus status) {
        return new User()
                .setUsername(username)
                .setPassword(password)
                .setEmail(username)
                .setFullName(fullName)
                .setRole(role)
                .setStatus(status)
                ;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegisterReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegisterReqDTO userRegisterReqDTO = (UserRegisterReqDTO) target;
        String fullName = userRegisterReqDTO.fullName;
        String username = userRegisterReqDTO.username;
        if (fullName.isEmpty()) {
            errors.rejectValue("fullName", "fullName.null", "Tên không được phép rỗng");
            return;
        }

        if (username.isEmpty()) {
            errors.rejectValue("username", "username.null", "Tên tài khoản không được phép rỗng");

        }
    }
    }

