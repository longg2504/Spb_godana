package com.godana.domain.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.godana.domain.dto.category.CategoryDTO;
import com.godana.domain.dto.postAvatar.PostAvatarResDTO;
import com.godana.domain.dto.user.UserDTO;
import com.godana.domain.entity.Category;
import com.godana.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private CategoryDTO category;
    private UserDTO user;
    private int like;
    private int comment;
    private List<PostAvatarResDTO> postAvatar;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;


    public PostDTO(Long id, String title, String content, Category category, User user, int like , int comment , Date createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category.toCategoryDTO();
        this.user = user.toUserDTO();
        this.like = like;
        this.comment = comment;
        this.createdAt = createdAt;
    }
}
