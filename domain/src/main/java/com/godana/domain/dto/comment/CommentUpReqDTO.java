package com.godana.domain.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentUpReqDTO implements Validator {
    @NotBlank(message = "nội dung không được rỗng")
    private String content;

    @Override
    public boolean supports(Class<?> clazz) {
        return CommentUpReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CommentUpReqDTO commentUpReqDTO = (CommentUpReqDTO) target;
        String content = commentUpReqDTO.content;

        if (content.isEmpty()) {
            errors.rejectValue("content", "content.null", "nội dung không được phép rỗng");
        }

    }
}
