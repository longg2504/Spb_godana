package com.godana.domain.dto.comment;

import com.godana.domain.dto.post.PostCreReqDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentCreReqDTO implements Validator {
    @NotBlank(message = "nội dung không được rỗng")
    private String content;
    private String postId;
    private String userId;

    @Override
    public boolean supports(Class<?> clazz) {
        return CommentCreReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CommentCreReqDTO commentCreReqDTO = (CommentCreReqDTO) target;
        String content = commentCreReqDTO.content;

        if (content.isEmpty()) {
            errors.rejectValue("content", "content.null", "nội dung không được phép rỗng");
        }

    }

}
