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
    private int titlenumericalOrder;

    public CategoryDTO(Long id, String title, int titlenumericalOrder) {
        this.id = id;
        this.title = title;
        this.titlenumericalOrder = titlenumericalOrder;
    }

    public Category toCategory() {
        return new Category()
                .setId(id)
                .setTitle(title)
                .setTitlenumericalOrder(titlenumericalOrder);
    }
}
