package com.godana.domain.dto.post;

import com.godana.domain.dto.category.CategoryDTO;
import com.godana.domain.dto.user.UserDTO;
import com.godana.domain.entity.Category;
import com.godana.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

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

    public PostDTO(Long id, String title, String content, Category category, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category.toCategoryDTO();
        this.user = user.toUserDTO();
    }
}
