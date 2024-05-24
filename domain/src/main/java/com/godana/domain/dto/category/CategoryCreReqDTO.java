package com.godana.domain.dto.category;

import com.godana.domain.dto.comment.CommentUpReqDTO;
import com.godana.domain.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CategoryCreReqDTO implements Validator {
    @NotBlank(message = "Tên danh mục không được rỗng")
    private String title;
    private String svg;

    public Category toCategory(){
        return new Category()
                .setId(null)
                .setTitle(title)
                .setTitlenumericalOrder(null)
                .setSvg(svg);

    }


    @Override
    public boolean supports(Class<?> clazz) {
        return CategoryCreReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CategoryCreReqDTO categoryCreReqDTO = (CategoryCreReqDTO) target;
        String title = categoryCreReqDTO.title;

        if (title.isEmpty()) {
            errors.rejectValue("title", "title.null", "Tên danh mục không được phép rỗng");
        }

    }
}
