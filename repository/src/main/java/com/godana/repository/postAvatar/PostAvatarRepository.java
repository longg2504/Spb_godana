package com.godana.repository.postAvatar;

import com.godana.domain.entity.Place;
import com.godana.domain.entity.PlaceAvatar;
import com.godana.domain.entity.Post;
import com.godana.domain.entity.PostAvatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostAvatarRepository extends JpaRepository<PostAvatar, String> {
    List<PostAvatar> findAllByPost(Post post);
}
