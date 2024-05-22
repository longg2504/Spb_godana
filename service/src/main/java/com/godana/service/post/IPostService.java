package com.godana.service.post;

import com.godana.domain.dto.post.*;
import com.godana.domain.entity.Category;
import com.godana.domain.entity.Post;
import com.godana.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IPostService extends IGeneralService<Post, Long> {
    Page<PostDTO> findAllByCategory(Category category, Pageable pageable);

    Optional<PostDTO> findAllByPostId(Long postId);


    List<Post> findAllByUserIdAndDeleted(Long userId, boolean deleted);

    PostCreResDTO createPost(PostCreReqDTO postCreReqDTO);

    PostUpResDTO updatePost(PostUpReqDTO postUpReqDTO, Long postId);

    PostCountDTO countPost();
}
