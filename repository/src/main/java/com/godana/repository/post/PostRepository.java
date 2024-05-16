package com.godana.repository.post;

import com.godana.domain.dto.place.PlaceCountDTO;
import com.godana.domain.dto.post.PostCountDTO;
import com.godana.domain.dto.post.PostDTO;
import com.godana.domain.entity.Category;
import com.godana.domain.entity.Post;
import com.godana.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT NEW com.godana.domain.dto.post.PostDTO ( " +
            "p.id," +
            "p.postTitle, " +
            "p.content," +
            "p.category," +
            "p.user, " +
            "CASE WHEN COUNT(DISTINCT l.id) is NULL THEN 0 ELSE COUNT(DISTINCT l.id) END, " +
            "CASE WHEN COUNT(DISTINCT c.id) is NULL THEN 0 ELSE COUNT(DISTINCT c.id) END, " +
            "p.createdAt " +
            ") " +
            "FROM Post as p " +
            "LEFT JOIN Like as l " +
            "ON l.post.id = p.id " +
            "LEFT JOIN Comment as c " +
            "ON c.post.id = p.id " +
            "WHERE (:category IS NULL OR p.category = :category) " +
            "AND p.deleted = false " +
            "GROUP BY p.id, p.postTitle, p.content, p.category, p.user, p.createdAt " +
            "ORDER BY p.createdAt DESC "
    )
    Page<PostDTO> findAllByCategory(@Param("category") Category category, Pageable pageable);

    @Query("SELECT NEW com.godana.domain.dto.post.PostCountDTO (" +
            "count(p.id)" +
            ") " +
            "FROM Post AS p " +
            "WHERE p.deleted = false "
    )
    PostCountDTO countPost();
}
