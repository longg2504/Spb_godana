package com.godana.service.post;

import com.godana.domain.dto.post.*;
import com.godana.domain.entity.Category;
import com.godana.domain.entity.Post;
import com.godana.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPostService extends IGeneralService<Post, Long> {
    Page<PostDTO> findAllByCategory(Category category, Pageable pageable);

    PostCreResDTO createPost(PostCreReqDTO postCreReqDTO);

    PostUpResDTO updatePost(PostUpReqDTO postUpReqDTO, Long postId);
}
