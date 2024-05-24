package com.godana.domain.dto.post;

import com.godana.domain.dto.place.PlaceUpReqDTO;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class PostCreReqDTO implements Validator{
    @NotBlank(message = "tiêu đề bài viết không được trống")
    private String postTitle;
    @NotBlank(message = "Nội dung bài viết không được phép trống")
    @Size(min= 0, max = 500, message = "Độ dài nội dụng nằm trong khoảng 0-500 ký tự!")
    private String content;
    private Long userId;
    private Long categoryId;
    private List<MultipartFile> images;

    @Override
    public boolean supports(Class<?> clazz) {
        return PostCreReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PostCreReqDTO postCreReqDTO = (PostCreReqDTO) target;
        String postTitle = postCreReqDTO.postTitle;
        String content = postCreReqDTO.content;

        if (postTitle.isEmpty()) {
            errors.rejectValue("postTitle", "postTitle.null", "tiêu đề bài viết không được phép rỗng");
            return;
        }


        if (content.isEmpty()) {
            errors.rejectValue("content", "content.null", "nội dung bài viết không được phép rỗng");
        }

    }
}
