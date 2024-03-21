package com.godana.service.post;

import com.godana.domain.dto.post.PostCreReqDTO;
import com.godana.domain.dto.post.PostCreResDTO;
import com.godana.domain.dto.post.PostUpReqDTO;
import com.godana.domain.dto.post.PostUpResDTO;
import com.godana.domain.entity.Post;
import com.godana.service.IGeneralService;

public interface IPostService extends IGeneralService<Post, Long> {

    PostCreResDTO createPost(PostCreReqDTO postCreReqDTO);

    PostUpResDTO updatePost(PostUpReqDTO postUpReqDTO, Long postId);
}
