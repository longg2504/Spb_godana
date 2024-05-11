package com.godana.service.category;

import com.godana.domain.dto.category.CategoryCountDTO;
import com.godana.domain.dto.category.CategoryCreReqDTO;
import com.godana.domain.dto.category.CategoryCreResDTO;
import com.godana.domain.dto.category.CategoryDTO;
import com.godana.domain.entity.Category;
import com.godana.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface ICategoryService extends IGeneralService<Category, Long> {
    List<CategoryDTO> findAllCategoryDTO();
    Optional<Category> findByIdAndDeletedFalse(Long id);

    Boolean existsByTitle(String title);

    CategoryCreResDTO createCategory(CategoryCreReqDTO categoryCreReqDTO);

    CategoryCountDTO countCategory();
}
