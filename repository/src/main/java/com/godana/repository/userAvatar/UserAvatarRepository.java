package com.godana.repository.userAvatar;

import com.godana.domain.entity.UserAvatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserAvatarRepository extends JpaRepository<UserAvatar, String> {
}
