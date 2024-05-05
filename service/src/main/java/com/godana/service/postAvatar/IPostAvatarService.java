package com.godana.service.postAvatar;

import com.godana.domain.entity.Post;
import com.godana.domain.entity.PostAvatar;
import com.godana.service.IGeneralService;

import java.util.List;

public interface IPostAvatarService extends IGeneralService<PostAvatar, String> {
    List<PostAvatar> findAllByPost(Post post);

}
