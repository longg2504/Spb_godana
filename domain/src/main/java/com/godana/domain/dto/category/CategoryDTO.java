package com.godana.domain.dto.category;

import com.godana.domain.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CategoryDTO {
    private Long id;
    private String title;
    private Long titlenumericalOrder;
    private String svg;

    public CategoryDTO(Long id, String title, Long titlenumericalOrder, String svg) {
        this.id = id;
        this.title = title;
        this.titlenumericalOrder = titlenumericalOrder;
        this.svg = svg;
    }

    public Category toCategory() {
        return new Category()
                .setId(id)
                .setTitle(title)
                .setTitlenumericalOrder(titlenumericalOrder)
                .setSvg(svg);
    }
}
