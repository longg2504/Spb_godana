package com.godana.api;

import com.godana.domain.dto.category.CategoryCreReqDTO;
import com.godana.domain.dto.user.ChangePasswordReqDTO;
import com.godana.domain.dto.user.UserReqUpDTO;
import com.godana.domain.dto.user.UserResDTO;
import com.godana.domain.entity.User;
import com.godana.domain.entity.UserAvatar;
import com.godana.exception.DataInputException;
import com.godana.exception.EmailExistsException;
import com.godana.service.user.IUserService;
import com.godana.service.userAvatar.IUserAvatarService;
import com.godana.utils.AppUtils;
import com.godana.utils.ValidateUtils;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserAPI {
    @Autowired
    private ValidateUtils validateUtils;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IUserAvatarService iUserAvatarService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AppUtils appUtils;

    @GetMapping
    public ResponseEntity<?> getAllUser(@RequestParam(defaultValue = "") String search, Pageable pageable){
        Page<UserResDTO> userResDTOPage = iUserService.findUserBySearch(search, pageable);
        if(userResDTOPage.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userResDTOPage, HttpStatus.OK);
    }

    @GetMapping("/list-ban")
    public ResponseEntity<?> getAllUserBand(@RequestParam(defaultValue = "") String search, Pageable pageable){
        Page<UserResDTO> userResDTOPage = iUserService.findUserBanBySearch(search, pageable);
        if(userResDTOPage.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userResDTOPage, HttpStatus.OK);
    }

    @PostMapping("/ban-user/{userId}")
    public ResponseEntity<?> banUser (@PathVariable("userId") String userIdStr){
        if(!validateUtils.isNumberValid(userIdStr)){
            throw new DataInputException("UserId không hợp lệ");
        }

        Long userId = Long.parseLong(userIdStr);
        Optional<User> userOptional = iUserService.findById(userId);
        if(!userOptional.isPresent()){
            throw new DataInputException("UserId không tồn tại");
        }
        User user = userOptional.get();

        iUserService.delete(user);

        return new ResponseEntity<>("User đã bị khoá trong hệ thống",HttpStatus.OK);
    }

    @PostMapping("/unban-user/{userId}")
    public ResponseEntity<?> unanUser (@PathVariable("userId") String userIdStr){
        if(!validateUtils.isNumberValid(userIdStr)){
            throw new DataInputException("UserId không hợp lệ");
        }

        Long userId = Long.parseLong(userIdStr);
        Optional<User> userOptional = iUserService.findById(userId);
        if(!userOptional.isPresent()){
            throw new DataInputException("UserId không tồn tại");
        }
        User user = userOptional.get();

        iUserService.unban(user);

        return new ResponseEntity<>("User đã được mở trong hệ thống",HttpStatus.OK);
    }

    @PostMapping("/change-password/{userId}")
    public ResponseEntity<?> changePassword (@PathVariable("userId") String userIdStr, @ModelAttribute ChangePasswordReqDTO changePasswordReqDTO){
        if(!validateUtils.isNumberValid(userIdStr)) {
            throw new DataInputException("UserId không hợp lệ");
        }
        Long userId = Long.parseLong(userIdStr);
        Optional<User> userOptional = iUserService.findById(userId);
        if(!userOptional.isPresent()){
            throw new DataInputException("UserId không tồn tại");
        }
        User user = userOptional.get();
        String oldPassword = changePasswordReqDTO.getOldPassword();
        if(passwordEncoder.matches(oldPassword, user.getPassword())){
            String newPassword = passwordEncoder.encode(changePasswordReqDTO.getNewPassword());
            user.setPassword(newPassword);

            iUserService.save(user);

            return new ResponseEntity<>("Đổi mật khẩu thành công",HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Mật khẩu cũ không đúng vui lòng xem lại", HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping("/update-user/{userId}")
    public ResponseEntity<?> updateUser(@Valid @PathVariable("userId") String userIdStr,@ModelAttribute UserReqUpDTO userReqUpDTO, BindingResult bindingResult){
        new UserReqUpDTO().validate(userReqUpDTO, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        if(!validateUtils.isNumberValid(userIdStr)){
            throw new DataInputException("Id user không đúng");
        }

        Long userId = Long.parseLong(userIdStr);
        User user = iUserService.update(userId, userReqUpDTO);

        UserResDTO userDTO = user.toUserResDTO();

        return new ResponseEntity<>(userDTO, HttpStatus.OK);

    }
}
