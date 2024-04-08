package com.godana.api;

import com.godana.domain.dto.user.UserLoginReqDTO;
import com.godana.domain.dto.user.AdminRegisterReqDTO;
import com.godana.domain.dto.user.UserRegisterReqDTO;
import com.godana.domain.entity.JwtResponse;
import com.godana.domain.entity.Role;
import com.godana.domain.entity.User;
import com.godana.domain.enums.EUserStatus;
import com.godana.exception.DataInputException;
import com.godana.exception.EmailExistsException;
import com.godana.exception.UnauthorizedException;
import com.godana.service.jwt.JwtService;
import com.godana.service.role.IRoleService;
import com.godana.service.user.IUserService;
import com.godana.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthAPI {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private AppUtils appUtils;

    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody AdminRegisterReqDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Boolean exitsByUsername = userService.existsByUsername(userDTO.getUsername());

        if (exitsByUsername) {
            throw new EmailExistsException("Tài khoản này đã tồn tại vui lòng xem lại!!!");
        }
        Optional<Role> optionalRole = roleService.findById(userDTO.getRoleId());

        if (!optionalRole.isPresent()) {
            throw new DataInputException("Role này không tồn tại vui lòng xem lại!!!");
        }
        try {
            userService.save(userDTO.toUser(optionalRole.get()));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Thông tin tài khoản không hợp lệ, vui lòng kiểm tra lại thông tin");
        }
    }

    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@Valid @ModelAttribute UserRegisterReqDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        Boolean exitsByUsername = userService.existsByUsername(userDTO.getUsername());

        if (exitsByUsername) {
            throw new EmailExistsException("Tài khoản này đã tồn tại vui lòng xem lại!!!");
        }
        try {
            userService.create(userDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Thông tin tài khoản không hợp lệ, vui lòng kiểm tra lại thông tin");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginReqDTO userLoginReqDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        String username = userLoginReqDTO.getUsername();
        String password = userLoginReqDTO.getPassword();
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataInputException("Dữ liệu không đúng! Vui lòng kiểm tra lại!");
        }
        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.getByUsername(username);
        currentUser.setStatus(EUserStatus.ONLINE);
        userService.save(currentUser);

        JwtResponse jwtResponse = null;


        if (currentUser.isDeleted()) {
            throw new UnauthorizedException("Tài khoản của bạn đã bị đình chỉ!");
        }

        if (currentUser.getRole().getCode().equals("ADMIN")) {

            jwtResponse = new JwtResponse(
                    jwt,
                    currentUser.getId(),
                    userDetails.getUsername(),
                    currentUser.getUsername(),
                    userDetails.getAuthorities()
            );
        } else {
            Optional<User> userOptional = userService.findByName(currentUser.getUsername());
            jwtResponse = new JwtResponse(

                    jwt,
                    currentUser.getId(),
                    userDetails.getUsername(),
                    currentUser.getUsername(),
                    userOptional.get().getUserAvatar(),
                    userDetails.getAuthorities()
            );
        }

            ResponseCookie springCookie = ResponseCookie.from("JWT", jwt)
                    .httpOnly(false)
                    .secure(false)
                    .path("/")
                    .maxAge(1000L * 60 * 60 * 24 * 30)
                    .domain("localhost")
                    .build();

            System.out.println(jwtResponse);

            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                    .body(jwtResponse);

        }
    }

