package com.godana.domain.entity;

import com.godana.domain.dto.comment.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "comments")
@Accessors(chain = true)
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String content;

    @ManyToOne
    private Comment commentParent;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id" , nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id" , referencedColumnName = "id", nullable = false)
    private User user;


    @OneToMany(mappedBy = "commentParent")
    private List<Comment> comments;

    public CommenCreResDTO toCommentCreResDTO(Date createAt){
        return new CommenCreResDTO()
                .setCommentId(id)
                .setContent(content)
                .setCommentParentId(null)
                .setPostId(post.getId())
                .setUserId(user.getId())
                .setCreatedAt(createAt)
                ;

    }

    public ReplyCreResDTO toReplyCreResDTO(Date createdAt, Long commentParentId){
        return new ReplyCreResDTO()
                .setCommentId(id)
                .setContent(content)
                .setCommentParentId(commentParentId)
                .setPostId(post.getId())
                .setUserId(user.getId())
                .setCreatedAt(createdAt)
        ;
    }

    public CommentResDTO toCommentResDTO(Date createdAt, Long count){
        return new CommentResDTO()
                .setCommentId(id)
                .setContent(content)
                .setUser(user.toUserResDTO())
                .setCreatedAt(createdAt)
                .setCountReply(count)
                ;
    }

    public ReplyResDTO toReplyResDTO(Date createdAt){
        return new ReplyResDTO()
                .setCommentId(id)
                .setUser(user.toUserResDTO())
                .setCreatedAt(createdAt);
    }
}
