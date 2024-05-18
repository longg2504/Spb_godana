package com.godana.service.comment;

import com.godana.domain.dto.comment.*;
import com.godana.domain.entity.Comment;
import com.godana.domain.entity.Post;
import com.godana.service.IGeneralService;

import java.util.List;

public interface ICommentService extends IGeneralService<Comment, Long> {
    List<CommentResDTO> findAllByPostId(Long postId);

    List<Comment> findAllByPost(Post post);


    List<ReplyResDTO> findAllByCommentParentId(Long commentParentId);
    CommenCreResDTO createComment(CommentCreReqDTO commentCreReqDTO);

    ReplyCreResDTO createReply(Long commentId, ReplyCreReqDTO replyCreReqDTO);

    CommentResDTO updateComment(CommentUpReqDTO commentUpReqDTO, Long commentId);
}
