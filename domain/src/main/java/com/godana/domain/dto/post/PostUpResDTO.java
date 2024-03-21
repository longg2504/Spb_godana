package com.godana.domain.dto.post;

import com.godana.domain.dto.avatar.AvatarResDTO;
import com.godana.domain.dto.category.CategoryDTO;
import com.godana.domain.dto.user.UserDTO;
import com.godana.domain.entity.Comment;
import com.godana.domain.entity.Like;
import com.godana.domain.entity.Reply;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class PostUpResDTO {
    private Long id;
    private String postTitle;
    private String content;
    private UserDTO user;
    private CategoryDTO category;
    private List<AvatarResDTO> postImages;
}
