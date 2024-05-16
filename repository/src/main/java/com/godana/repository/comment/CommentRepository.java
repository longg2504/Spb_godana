package com.godana.repository.comment;

import com.godana.domain.dto.comment.CommentResDTO;
import com.godana.domain.dto.comment.ReplyResDTO;
import com.godana.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT NEW com.godana.domain.dto.comment.CommentResDTO (" +
            "c1.id, " +
            "c1.content," +
            "c1.user, " +
            "c1.createdAt," +
            "COUNT(c2.id) " +
            ") " +
            "FROM Comment as c1 " +
            "LEFT JOIN Comment as c2 " +
            "ON c1.id = c2.commentParent.id " +
            "WHERE c1.post.id = :postId " +
            "AND c1.commentParent.id IS NULL " +
            "AND c1.deleted = false " +
            "GROUP BY c1.id "
    )
    List<CommentResDTO> findAllByPostId(Long postId);

    @Query("SELECT NEW com.godana.domain.dto.comment.ReplyResDTO (" +
            "c1.id, " +
            "c1.content, " +
            "c1.user, " +
            "c1.createdAt, "  +
            "COUNT(c2.id) " +
            ") " +
            "FROM Comment as c1 " +
            "LEFT JOIN Comment as c2 " +
            "ON c1.id = c2.commentParent.id " +
            "WHERE c1.commentParent.id = :commentParentId " +
            "AND c1.deleted = false " +
            "GROUP BY c1.id "
    )
    List<ReplyResDTO> findAllByCommentParentId(Long commentParentId);
}
