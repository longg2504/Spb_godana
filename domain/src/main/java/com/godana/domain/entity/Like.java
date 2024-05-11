package com.godana.domain.entity;

import com.godana.domain.dto.like.LikeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="likes")
@Accessors(chain = true)
public class Like extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id" , referencedColumnName = "id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name="user_id" , referencedColumnName = "id" , nullable = false)
    private User user;

    public LikeDTO toLikeDTO(int like , int comment){
        return new LikeDTO()
                .setPost(post.toPostDTO(like, comment));
    }
}
