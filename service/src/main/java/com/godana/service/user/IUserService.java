package com.godana.service.user;


import com.godana.domain.entity.User;
import com.godana.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface IUserService extends IGeneralService<User, Long>, UserDetailsService {

    Boolean existsByUsername(String username);

    User getByUsername(String username);

    Optional<User> findByName(String userName);




}
