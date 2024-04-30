package com.godana.domain.dto.category;

import com.godana.domain.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CategoryCreReqDTO {
    private String title;
    private String svg;

    public Category toCategory(){
        return new Category()
                .setId(null)
                .setTitle(title)
                .setTitlenumericalOrder(null)
                .setSvg(svg);

    }
}
