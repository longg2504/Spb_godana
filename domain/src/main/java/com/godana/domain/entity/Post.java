package com.godana.domain.entity;

import com.godana.domain.dto.avatar.AvatarResDTO;
import com.godana.domain.dto.post.PostCreResDTO;
import com.godana.domain.dto.post.PostUpResDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "posts")
@Accessors(chain = true)
public class Post extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_title")
    private String postTitle;

    @Column(length = Integer.MAX_VALUE)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id" , nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name= "category_id" , referencedColumnName = "id" , nullable = false)
    private Category category;

    @OneToMany(mappedBy = "post")
    private List<Like> likes;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @OneToMany(mappedBy = "post")
    private List<PostAvatar> postImages;



    public PostUpResDTO toPostUpResDTO(List<PostAvatar> postAvatars) {
        return new PostUpResDTO()
                .setId(id)
                .setPostTitle(postTitle)
                .setContent(content)
                .setUser(user.toUserDTO())
                .setCategory(category.toCategoryDTO())
                .setPostImages(toAvatarResDTOList(postAvatars));
    }

    public PostCreResDTO toPostCreResDTO(List<PostAvatar> postAvatars) {
        return new PostCreResDTO()
                .setId(id)
                .setPostTitle(postTitle)
                .setContent(content)
                .setUser(user.toUserDTO())
                .setCategory(category.toCategoryDTO())
                .setPostImages(toAvatarResDTOList(postAvatars));
    }

    public PostCreResDTO toPostCreResDTO() {
        return new PostCreResDTO()
                .setId(id)
                .setPostTitle(postTitle)
                .setContent(content)
                .setUser(user.toUserDTO())
                .setCategory(category.toCategoryDTO());
    }


    public List<AvatarResDTO> toAvatarResDTOList(List<PostAvatar> postAvatars){
        List<AvatarResDTO> dtoList = new ArrayList<>();
        for (PostAvatar postAvatar : postAvatars) {
            dtoList.add(postAvatar.toAvatarResDTO());
        }
        return dtoList;
    }
}
