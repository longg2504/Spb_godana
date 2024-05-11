package com.godana.domain.dto.category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryCountDTO {
    private Long countCategory;

    public CategoryCountDTO(Long countCategory) {
        this.countCategory = countCategory;
    }
}
