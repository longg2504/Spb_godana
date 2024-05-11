package com.godana.service.like;

import com.godana.domain.dto.like.LikeReqDTO;
import com.godana.domain.entity.Like;
import com.godana.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface ILikeService extends IGeneralService<Like, Long> {
    void create(LikeReqDTO likeReqDTO);
    Optional<Like> findByUserIdAndPostId(Long userId, Long postId);
    List<Like> findByUserId(Long userId);

}
