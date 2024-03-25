package com.godana.repository.postAvatar;

import com.godana.domain.entity.PostAvatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostAvatarRepository extends JpaRepository<PostAvatar, String> {
}
